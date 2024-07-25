package com.shop.java_app.auth.service;

import com.shop.java_app.auth.dto.SignInRequest;
import com.shop.java_app.auth.dto.SignUpRequest;
import com.shop.java_app.auth.dto.TokenResponse;
import com.shop.java_app.auth.entity.BlackList;
import com.shop.java_app.auth.entity.RefreshToken;
import com.shop.java_app.auth.entity.TokenType;
import com.shop.java_app.auth.jwt.JwtTokenProvider;
import com.shop.java_app.auth.repository.BlackListRepository;
import com.shop.java_app.auth.repository.RefreshTokenRepository;
import com.shop.java_app.auth.security.CustomUserDetails;
import com.shop.java_app.auth.security.CustomUserDetailsService;
import com.shop.java_app.common.exception.DuplicationException;
import com.shop.java_app.common.exception.ErrorCode;
import com.shop.java_app.common.exception.NotFoundException;
import com.shop.java_app.user.entity.User;
import com.shop.java_app.user.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlackListRepository blackListRepository;

    @Value("${jwt.type}")
    private String authType;

    @Value("${jwt.refresh-expiration-milliseconds}")
    private long refreshExpirationTime;

    @Transactional
    public void signup(final SignUpRequest request) {
        validateEmailDuplicate(request.getEmail());
        validatePhoneNumberDuplicate(request.getPhoneNumber());
        userRepository.save(request.toEntity(passwordEncoder.encode(request.getPassword())));
    }

    @Transactional
    public TokenResponse signin(final SignInRequest request) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        final String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
        deleteAndCreateRefreshToken(userDetails, refreshToken);

        return TokenResponse.builder()
                .authType(authType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void signout(final HttpServletRequest request) {
        final String accessToken = jwtTokenProvider.resolveToken(request);
        validateToken(accessToken, TokenType.ACCESS);

        final Long userId = jwtTokenProvider.extractUserId(accessToken);
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));

        final Date expirationTime = jwtTokenProvider.extractExpiration(accessToken);

        // 만료시간 - 현재시간 = 만료시간까지 남은 시간
        final long accessExpirationTime = expirationTime.getTime() - new Date().getTime();

        // Black List 데이터 저장
        blackListRepository.save(new BlackList(user.getId(), accessToken, accessExpirationTime));

        // Refresh Token 데이터 삭제
        refreshTokenRepository.deleteById(user.getId());
    }

    @Transactional
    public TokenResponse reissue(final HttpServletRequest request) {
        final String refreshToken = jwtTokenProvider.resolveToken(request);
        validateToken(refreshToken, TokenType.REFRESH);

        final Long userId = jwtTokenProvider.extractUserId(refreshToken);
        final RefreshToken refreshTokenInfo = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));

        // user id 검증
        if (!Objects.equals(userId, refreshTokenInfo.getId())) {
            throw new JwtException(ErrorCode.INVALID_TOKEN.getMessage());
        }

        final User user = userRepository.findById(refreshTokenInfo.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));

        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(user.getEmail());
        final String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);
        final String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
        deleteAndCreateRefreshToken(userDetails, newRefreshToken);

        return TokenResponse.builder()
                .authType(authType)
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private void validateEmailDuplicate(final String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicationException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    private void validatePhoneNumberDuplicate(final String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicationException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }
    }

    private void validateToken(final String token, final TokenType tokenType) {
        if (token == null
                || !jwtTokenProvider.isValidateToken(token)
                || jwtTokenProvider.extractTokenType(token) != tokenType) {
            throw new JwtException(ErrorCode.INVALID_TOKEN.getMessage());
        }
    }

    private void deleteAndCreateRefreshToken(final CustomUserDetails userDetails, final String refreshToken) {
        refreshTokenRepository.deleteById(userDetails.getUserId());
        saveAuthToken(userDetails.getUserId(), refreshToken);
    }

    private void saveAuthToken(final Long userId, final String refreshToken) {
        refreshTokenRepository.save(new RefreshToken(userId, refreshToken, refreshExpirationTime));
    }
}
