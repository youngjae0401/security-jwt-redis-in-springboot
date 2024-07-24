package com.shop.java_app.auth.dto;

import com.shop.java_app.auth.entity.Role;
import com.shop.java_app.user.entity.User;
import lombok.Getter;

@Getter
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;

    public User toEntity(String encodePassword) {
        return User.builder()
                .name(name)
                .password(encodePassword)
                .email(email)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }
}
