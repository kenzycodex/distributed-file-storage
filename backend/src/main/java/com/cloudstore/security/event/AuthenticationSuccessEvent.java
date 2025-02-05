package com.cloudstore.security.event;

import com.cloudstore.model.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AuthenticationSuccessEvent extends ApplicationEvent {
    private final String username;

    public AuthenticationSuccessEvent(User user) {
        super(user);
        this.username = user.getUsername();
    }
}
