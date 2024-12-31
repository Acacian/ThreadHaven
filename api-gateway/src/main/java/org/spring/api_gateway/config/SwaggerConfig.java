package org.spring.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

    private final WebClient webClient;

    public SwaggerConfig(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Bean
    public GroupedOpenApi userServiceApi() {
        return GroupedOpenApi.builder()
                .group("user-service")
                .addOpenApiCustomiser(openApi -> {
                    String apiDocs = fetchApiDocs("http://localhost:8081/v3/api-docs");
                    openApi.setExtensions(Map.of("user-service", apiDocs));
                })
                .pathsToMatch("/user-service/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authServiceApi() {
        return GroupedOpenApi.builder()
                .group("auth-service")
                .addOpenApiCustomiser(openApi -> {
                    String apiDocs = fetchApiDocs("http://localhost:8082/v3/api-docs");
                    openApi.setExtensions(Map.of("auth-service", apiDocs));
                })
                .pathsToMatch("/auth-service/**")
                .build();
    }

    private String fetchApiDocs(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 동기 방식으로 API 스펙 가져오기
    }
}
