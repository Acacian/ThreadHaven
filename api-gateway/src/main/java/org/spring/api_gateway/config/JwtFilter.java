package org.spring.api_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtFilter {

    private final SecretKey secretKey;

    public JwtFilter() {
        // JWT 비밀키를 환경 변수에서 로드하거나 하드코딩된 값으로 설정
        String secret = System.getenv("JWT_SECRET"); // 환경 변수 사용
        if (secret == null || secret.isEmpty()) {
            throw new IllegalStateException("JWT_SECRET is not set");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Mono<Void> filter(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return Mono.error(new RuntimeException("Missing Authorization Header"));
        }

        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            return Mono.error(new RuntimeException("Invalid Authorization Header"));
        }

        token = token.substring(7); // "Bearer " 제거

        if (!isTokenValid(token)) {
            return Mono.error(new RuntimeException("Invalid or Expired Token"));
        }

        return Mono.empty();
    }

    private boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

            // 추가 검증 로직 (예: 토큰의 발급자나 대상 확인)
            String username = claims.getSubject();
            if (username == null || username.isEmpty()) {
                return false;
            }

            return true;
        } catch (Exception e) {
            // JWT 검증 실패 (서명 오류, 만료된 토큰 등)
            return false;
        }
    }
}
