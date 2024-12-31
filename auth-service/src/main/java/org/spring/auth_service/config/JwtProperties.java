package org.spring.auth_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private long expiration;

    // Getters and Setters
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @PostConstruct
    public void validateProperties() {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("JWT_SECRET must be set");
        }
        if (expiration <= 0) {
            throw new IllegalArgumentException("JWT_EXPIRATION must be a positive value");
        }
    }
}
