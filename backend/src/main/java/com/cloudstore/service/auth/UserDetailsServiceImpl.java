package com.cloudstore.service.auth;

import com.cloudstore.model.entity.User;
import com.cloudstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "userDetails", key = "#username", unless = "#result == null")
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);
        
        return userRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
    }

    private UserDetails createUserDetails(User user) {
        if (!user.isEnabled()) {
            log.warn("Attempt to load disabled user: {}", user.getUsername());
            throw new UsernameNotFoundException("User account is disabled");
        }
        
        if (!user.isAccountNonLocked()) {
            log.warn("Attempt to load locked user: {}", user.getUsername());
            throw new UsernameNotFoundException("User account is locked");
        }

        return user;
    }
}