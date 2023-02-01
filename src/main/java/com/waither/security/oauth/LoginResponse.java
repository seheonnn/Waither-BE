package com.waither.security.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private Role role;
    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponse(Long id, Role role, String tokenType, String accessToken, String refreshToken) {
        this.id = id;
        this.role = role;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
