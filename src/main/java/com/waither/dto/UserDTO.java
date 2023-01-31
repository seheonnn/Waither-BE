package com.waither.dto;

import com.waither.entities.UserEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    private Long userIdx;

    private String userName;

    private String email;

    private String authId;

    private String pw;

//    private String refreshToken;

    private String provider;

    private String role;

    private int status;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userName(userName)
                .email(email)
                .authId(authId)
                .pw(pw)
//                .refreshToken(refreshToken)
                .provider(provider)
                .role(role)
                .status(status)
                .build();
    }

}
