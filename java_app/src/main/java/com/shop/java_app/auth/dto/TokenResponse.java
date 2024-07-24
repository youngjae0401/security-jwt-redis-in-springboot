package com.shop.java_app.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String authType;
    private String accessToken;
    private String refreshToken;
}
