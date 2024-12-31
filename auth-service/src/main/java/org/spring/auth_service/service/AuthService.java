package org.spring.auth_service.service;

import org.spring.auth_service.config.JwtProperties;
import org.spring.auth_service.dto.LoginRequest;
import org.spring.auth_service.redis.TokenBlacklist;
import org.spring.auth_service.util.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklist tokenBlacklist;
    private final WebClient webClient;

    public AuthService(JwtTokenProvider jwtTokenProvider, TokenBlacklist tokenBlacklist, WebClient.Builder webClientBuilder, JwtProperties jwtProperties) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlacklist = tokenBlacklist;
        this.webClient = webClientBuilder.baseUrl(jwtProperties.getSecret()).build();
    }

    public String authenticateAndGenerateToken(LoginRequest loginRequest) {
        boolean isAuthenticated = authenticate(loginRequest);
        if (!isAuthenticated) {
            throw new RuntimeException("Invalid username or password");
        }
        return jwtTokenProvider.createToken(loginRequest.getUsername());
    }

    public void logout(String token) {
        long expiration = jwtTokenProvider.getTokenExpiration(token);
        tokenBlacklist.addTokenToBlacklist(token, expiration);
    }

    public boolean validateToken(String token) {
        if (tokenBlacklist.isTokenBlacklisted(token)) {
            return false;
        }
        return jwtTokenProvider.validateToken(token);
    }

    private boolean authenticate(LoginRequest loginRequest) {
        String endpoint = "/authenticate";
        try {
            Boolean isAuthenticated = webClient.post()
                    .uri(endpoint)
                    .bodyValue(loginRequest)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            return Boolean.TRUE.equals(isAuthenticated);
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate user", e);
        }
    }
}
