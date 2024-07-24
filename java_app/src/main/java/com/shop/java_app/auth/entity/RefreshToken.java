package com.shop.java_app.auth.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "RefreshToken")
public class RefreshToken {
    @Id
    private Long id;

    @Indexed
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long refreshExpirationTime;

    public RefreshToken(Long id, String refreshToken, long refreshExpirationTime) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.refreshExpirationTime = refreshExpirationTime;
    }
}
