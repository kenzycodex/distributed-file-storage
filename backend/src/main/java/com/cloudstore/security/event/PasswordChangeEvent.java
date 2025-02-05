package com.cloudstore.security.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordChangeEvent extends ApplicationEvent {
    private final String username;
    private final boolean success;

    public PasswordChangeEvent(String username, boolean success) {
        super(username);
        this.username = username;
        this.success = success;
    }
}
