package com.cloudstore.shared.constants;

public enum UserRoles {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_GUEST("GUEST");

    private final String role;

    UserRoles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}