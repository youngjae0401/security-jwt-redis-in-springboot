package com.shop.java_app.auth.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "BlackList")
public class BlackList {
    @Id
    private Long id;

    @Indexed
    private String accessToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long accessExpirationTime;

    public BlackList(Long id, String accessToken, long accessExpirationTime) {
        this.id = id;
        this.accessToken = accessToken;
        this.accessExpirationTime = accessExpirationTime;
    }
}
