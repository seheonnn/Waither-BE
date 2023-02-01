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

//<<<<<<< HEAD
//    private String id;

//    private String pw;

//    private String refreshToken;
//=======
    private String authId;

    private String pw;

//    private String refreshToken;
//>>>>>>> 995927066e5286e98feac4ae7e25e6b01c5eba43

    private String provider;

    private String role;

//<<<<<<< HEAD
    private char status;
//=======
//    private int status;
//>>>>>>> 995927066e5286e98feac4ae7e25e6b01c5eba43

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userName(userName)
                .email(email)
//<<<<<<< HEAD
//                .id(id)
//                .pw(pw)
//                .refreshToken(refreshToken)
//=======
                .authId(authId)
                .pw(pw)
//                .refreshToken(refreshToken)
//>>>>>>> 995927066e5286e98feac4ae7e25e6b01c5eba43
                .provider(provider)
                .role(role)
                .status(status)
                .build();
    }

}
