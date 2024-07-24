package com.shop.java_app.auth.dto;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String email;
    private String password;
}
