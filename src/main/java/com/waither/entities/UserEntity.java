package com.waither.entities;

import com.waither.dto.UserDTO;
import lombok.*;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "User")
@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sequence")
    private Long userIdx;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "authId", nullable = false)
    private String authId;

    @Column(name = "pw", nullable = true)
    private String pw;

//    @Column(name = "refreshToken") // 로그인 토큰 - 어플 특성상 제외
//    private String refreshToken;

    @Column(name = "provider") // 로그인 형태 (ex: 카카오, 애플 ...)
    private String provider;

    @Column(name = "role", nullable = false) // User, Guest
    private String role;

    @Column(name = "status", nullable = false) // 상태 : 탈퇴 시 0 가입 시 1
    private int status;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .userName(userName)
                .email(email)
                .authId(authId)
                .pw(pw)
//                .refreshToken(refreshToken)
                .provider(provider)
                .role(provider)
                .status(status)
                .build();
    }
}
