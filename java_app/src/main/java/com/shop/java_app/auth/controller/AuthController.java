package com.shop.java_app.auth.controller;

import com.shop.java_app.auth.dto.SignInRequest;
import com.shop.java_app.auth.dto.SignUpRequest;
import com.shop.java_app.auth.dto.TokenResponse;
import com.shop.java_app.auth.service.AuthService;
import com.shop.java_app.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseDto<Void> signup(@RequestBody @Valid final SignUpRequest request) {
        authService.signup(request);
        return ResponseDto.success();
    }

    @PostMapping("/signin")
    public ResponseDto<TokenResponse> signin(@RequestBody @Valid final SignInRequest request) {
        return ResponseDto.success(authService.signin(request));
    }

    @PostMapping("/signout")
    public ResponseDto<Void> signout(final HttpServletRequest request) {
        authService.signout(request);
        return ResponseDto.success();
    }

    @PostMapping("/reissue")
    public ResponseDto<TokenResponse> reissue(final HttpServletRequest request) {
        return ResponseDto.success(authService.reissue(request));
    }
}
