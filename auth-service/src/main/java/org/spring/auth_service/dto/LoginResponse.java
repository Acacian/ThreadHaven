package org.spring.auth_service.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String message;
}
