package com.waither.entities;

import com.waither.dto.UserDTO;
//<<<<<<< HEAD
//import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
//=======
//import lombok.*;
//
//import javax.persistence.*;
//>>>>>>> 995927066e5286e98feac4ae7e25e6b01c5eba43

@Entity(name = "User")
@Table(name = "User")
@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
//<<<<<<< HEAD
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
//=======
//>>>>>>> 995927066e5286e98feac4ae7e25e6b01c5eba43
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sequence")
    private Long userIdx;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = true)
    private String email;

//<<<<<<< HEAD
    @Column(name = "authId", nullable = true)
    private String authId;

    @Column(name = "pw", nullable = false)
    private String pw;

//    @Column(name = "refreshToken") // 로그인 토큰
//    private String refreshToken;
//=======
//    @Column(name = "authId", nullable = false)
//    private String authId;

//    @Column(name = "pw", nullable = true)
//    private String pw;

//    @Column(name = "refreshToken") // 로그인 토큰 - 어플 특성상 제외
//    private String refreshToken;
//>>>>>>> 995927066e5286e98feac4ae7e25e6b01c5eba43

    @Column(name = "provider") // 로그인 형태 (ex: 카카오, 애플 ...)
    private String provider;

    @Column(name = "role", nullable = false) // User, Guest
    private String role;

    @Column(name = "status", nullable = false)
    @ColumnDefault("'A'")
    private char status;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .userName(userName)
                .email(email)
//<<<<<<< HEAD
//                .id(id)
//                .pw(pw)
//                .refreshToken(refreshToken)
//                .provider(provider)
//                .role(provider)
//                .role(role)
//=======
                .authId(authId)
                .pw(pw)
//                .refreshToken(refreshToken)
                .provider(provider)
                .role(provider)
//>>>>>>> 995927066e5286e98feac4ae7e25e6b01c5eba43
                .status(status)
                .build();
    }
}
