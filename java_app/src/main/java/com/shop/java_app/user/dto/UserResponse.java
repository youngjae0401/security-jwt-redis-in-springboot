package com.shop.java_app.user.dto;

import com.shop.java_app.auth.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long userId;
    private String email;
    private String name;
    private String phoneNumber;
    private Role role;
}
