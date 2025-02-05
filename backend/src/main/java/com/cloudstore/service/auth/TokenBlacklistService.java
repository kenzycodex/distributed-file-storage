package com.cloudstore.service.auth;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {
    private final Cache<String, Boolean> blacklistedTokens;

    public TokenBlacklistService(JwtService jwtService) {
        this.blacklistedTokens = Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10_000)
                .build();
    }

    public void blacklistToken(String token) {
        blacklistedTokens.put(token, true);
    }

    public boolean isBlacklisted(String token) {
        Boolean isBlacklisted = blacklistedTokens.getIfPresent(token);
        return isBlacklisted != null && isBlacklisted;
    }

    public void cleanupExpiredTokens() {
        blacklistedTokens.cleanUp();
    }
}