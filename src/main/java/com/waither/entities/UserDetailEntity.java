package com.waither.entities;

import com.waither.dto.UserDTO;
import com.waither.dto.UserDetailDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "UserDetail")
@Table(name = "UserDetail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailEntity {

    @Id
    @SequenceGenerator(name = "user_detail_sequence", sequenceName = "user_detail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_detail_sequence")
    private Long userIdx;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "refreshToken") // 로그인 토큰
    private String refreshToken;

    @Column(name = "provider") // 로그인 형태 (ex: 카카오, 애플 ...)
    private String provider;

    @Column(name = "role", nullable = false) // User, Guest
    private String role;

    public UserDetailDTO toDTO() {
        return UserDetailDTO.builder()
                .userName(userName)
                .email(email)
                .id(id)
                .pw(pw)
                .refreshToken(refreshToken)
                .provider(provider)
                .role(provider)
                .build();
    }
}
