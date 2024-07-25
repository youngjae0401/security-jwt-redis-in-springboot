package com.shop.java_app.auth.entity;

public enum Role {
    USER("유저"),
    ADMIN("관리자");

    private final String roleName;

    Role(final String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
