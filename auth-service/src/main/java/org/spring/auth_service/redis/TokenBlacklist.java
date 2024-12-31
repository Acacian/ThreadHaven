package org.spring.auth_service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class TokenBlacklist {

    private final RedisTemplate<String, String> redisTemplate;

    public TokenBlacklist(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addTokenToBlacklist(String token, long expirationInMillis) {
        redisTemplate.opsForValue().set(token, "BLACKLISTED", Duration.ofMillis(expirationInMillis));
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(token);
    }
}
