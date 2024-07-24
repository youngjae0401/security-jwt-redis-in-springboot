package com.shop.java_app.auth.jwt;

import com.shop.java_app.auth.entity.TokenType;
import com.shop.java_app.auth.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key secretkey;
    private final long accessExpirationTime;
    private final long refreshExpirationTime;

    @Value("${jwt.type}")
    private String authType;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String key,
                            @Value("${jwt.access-expiration-milliseconds}") long accessExpirationMilliseconds,
                            @Value("${jwt.refresh-expiration-milliseconds}") long refreshExpirationMilliseconds) {
        secretkey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        accessExpirationTime = accessExpirationMilliseconds;
        refreshExpirationTime = refreshExpirationMilliseconds;
    }

    public String resolveToken(HttpServletRequest request) {
        String standardCharacter = authType + " ";
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.startsWith(standardCharacter))
                .map(token -> token.substring(standardCharacter.length()))
                .orElse(null);
    }

    public String generateAccessToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = createClaims(userDetails);
        claims.put("type", TokenType.ACCESS);
        return generateToken(userDetails, claims, accessExpirationTime);
    }

    public String generateRefreshToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = createClaims(userDetails);
        claims.put("type", TokenType.REFRESH);
        return generateToken(userDetails, claims, refreshExpirationTime);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractUserId(String token) {
        return Optional.ofNullable(extractClaims(token).get("id"))
                .filter(Integer.class::isInstance)
                .map(id -> ((Integer) id).longValue())
                .orElse(null);
    }

    public TokenType extractTokenType(String token) {
        return Optional.ofNullable(extractClaims(token).get("type"))
                .filter(String.class::isInstance)
                .map(type -> TokenType.valueOf((String) type))
                .orElse(null);
    }

    public boolean isValidateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) secretkey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("[JWT ERROR] '" + token + "'은 유효하지 않은 토큰입니다. ({})", e.getMessage());
            throw e;
        }
    }

    private String generateToken(CustomUserDetails userDetails,
                                 Map<String, Object> claims,
                                 long expirationTime) {
        return Jwts.builder()
                .subject(userDetails.getEmail())
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretkey)
                .compact();
    }

    private <T> T extractClaim(String token,
                               Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) secretkey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Map<String, Object> createClaims(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getUserId());
        claims.put("name", userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities());
        return claims;
    }
}
