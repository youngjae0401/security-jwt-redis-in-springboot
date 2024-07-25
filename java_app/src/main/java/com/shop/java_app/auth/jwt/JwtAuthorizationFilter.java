package com.shop.java_app.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.java_app.auth.entity.TokenType;
import com.shop.java_app.auth.repository.BlackListRepository;
import com.shop.java_app.auth.security.CustomUserDetails;
import com.shop.java_app.auth.security.CustomUserDetailsService;
import com.shop.java_app.common.dto.ResponseDto;
import com.shop.java_app.common.exception.ErrorCode;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final BlackListRepository blackListRepository;

    public JwtAuthorizationFilter(final JwtTokenProvider jwtTokenProvider,
                                  final CustomUserDetailsService userDetailsService,
                                  final BlackListRepository blackListRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.blackListRepository = blackListRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // 토큰이 있는지, 유효한 토큰인지, 액세스 토큰인지, 블랙리스트에 담겨 있는 토큰인지
            final String accessToken = jwtTokenProvider.resolveToken(request);
            if (accessToken != null
                    && jwtTokenProvider.isValidateToken(accessToken)
                    && jwtTokenProvider.extractTokenType(accessToken) == TokenType.ACCESS
                    && !blackListRepository.existsByAccessToken(accessToken)) {
                final String email = jwtTokenProvider.extractEmail(accessToken);
                final CustomUserDetails userDetails = (CustomUserDetails) this.userDetailsService.loadUserByUsername(email);

                final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (JwtException | IllegalArgumentException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), ResponseDto.fail(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_TOKEN.getMessage()));
        }

        filterChain.doFilter(request, response);
    }
}
