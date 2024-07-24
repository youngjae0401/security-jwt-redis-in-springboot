package com.shop.java_app.auth.dto;

import com.shop.java_app.auth.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthUserResponse {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
}
