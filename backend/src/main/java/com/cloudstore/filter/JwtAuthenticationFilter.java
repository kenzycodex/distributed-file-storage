package com.cloudstore.filter;

import com.cloudstore.service.auth.JwtService;
import com.cloudstore.service.auth.TokenBlacklistService;
import com.cloudstore.service.auth.UserDetailsServiceImpl;
import com.cloudstore.exception.JwtAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenBlacklistService blacklistService;
    private final ObjectMapper objectMapper;
    private final Bucket rateLimitBucket;

    private final RequestMatcher publicPaths = new OrRequestMatcher(
        Arrays.asList(
            new AntPathRequestMatcher("/api/v1/auth/login"),
            new AntPathRequestMatcher("/api/v1/auth/register"),
            new AntPathRequestMatcher("/api/v1/auth/refresh-token"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**")
        )
    );

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return publicPaths.matches(request);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        try {
            // Rate limiting check
            if (!checkRateLimit(response, request)) {
                return;
            }

            // JWT processing
            String token = extractTokenFromRequest(request);
            if (token != null) {
                processToken(token, request);
            }

            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            log.error("JWT Authentication failed: {}", e.getMessage());
            handleError(response, HttpStatus.UNAUTHORIZED, e.getMessage(), request);
        } catch (Exception e) {
            log.error("Authentication error occurred", e);
            handleError(response, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
        }
    }

    private boolean checkRateLimit(HttpServletResponse response, HttpServletRequest request) throws IOException {
        ConsumptionProbe probe = rateLimitBucket.tryConsumeAndReturnRemaining(1);
        if (!probe.isConsumed()) {
            handleError(response, HttpStatus.TOO_MANY_REQUESTS,
                "Rate limit exceeded. Try again in " + probe.getNanosToWaitForRefill() / 1_000_000_000 + " seconds", request);
            return false;
        }
        return true;
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private void processToken(String token, HttpServletRequest request) {
        if (blacklistService.isBlacklisted(token)) {
            log.warn("Attempt to use blacklisted token");
            throw new JwtAuthenticationException("Token has been revoked");
        }

        String username = jwtService.extractUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            validateToken(token, userDetails);
            
            UsernamePasswordAuthenticationToken authToken = createAuthenticationToken(userDetails, request);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            
            log.debug("User '{}' successfully authenticated", username);
        }
    }

    private void validateToken(String token, UserDetails userDetails) {
        if (!jwtService.isTokenValid(token, userDetails)) {
            log.warn("Invalid token for user: {}", userDetails.getUsername());
            throw new JwtAuthenticationException("Invalid token");
        }

        if (!userDetails.isEnabled()) {
            log.warn("Attempt to authenticate disabled user: {}", userDetails.getUsername());
            throw new JwtAuthenticationException("User account is disabled");
        }

        if (!userDetails.isAccountNonLocked()) {
            log.warn("Attempt to authenticate locked user: {}", userDetails.getUsername());
            throw new JwtAuthenticationException("User account is locked");
        }
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authToken;
    }

    private void handleError(HttpServletResponse response, HttpStatus status, String message, HttpServletRequest request) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        errorDetails.put("timestamp", Instant.now().toString());
        errorDetails.put("path", request.getRequestURI());

        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}