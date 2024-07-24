package com.shop.java_app.auth.security;

import com.shop.java_app.auth.dto.AuthUserResponse;
import com.shop.java_app.common.exception.ErrorCode;
import com.shop.java_app.common.exception.NotFoundException;
import com.shop.java_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new CustomUserDetails(AuthUserResponse.builder()
                        .userId(user.getId())
                        .name(user.getName())
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build()))
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));
    }
}
