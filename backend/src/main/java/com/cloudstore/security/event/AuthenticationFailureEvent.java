package com.cloudstore.security.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AuthenticationFailureEvent extends ApplicationEvent {
    private final String username;

    public AuthenticationFailureEvent(String username) {
        super(username);
        this.username = username;
    }
}
