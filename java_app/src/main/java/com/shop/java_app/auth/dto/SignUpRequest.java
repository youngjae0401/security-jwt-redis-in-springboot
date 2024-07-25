package com.shop.java_app.auth.dto;

import com.shop.java_app.auth.entity.Role;
import com.shop.java_app.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "휴대폰번호를 입력해주세요.")
    private String phoneNumber;

    @NotNull(message = "권한을 입력해주세요.")
    private Role role;

    public User toEntity(final String encodePassword) {
        return User.builder()
                .name(name)
                .password(encodePassword)
                .email(email)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }
}
