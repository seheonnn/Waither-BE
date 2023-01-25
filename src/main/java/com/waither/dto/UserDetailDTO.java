package com.waither.dto;

import com.waither.entities.UserDetailEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDetailDTO {

    private Long userIdx;

    private String userName;

    private String email;

    private String id;

    private String pw;

    private String refreshToken;

    private String provider;

    private String role;

    public UserDetailEntity toEntity() {
        return UserDetailEntity.builder()
                .userName(userName)
                .email(email)
                .id(id)
                .pw(pw)
                .refreshToken(refreshToken)
                .provider(provider)
                .role(role)
                .build();
    }

}
