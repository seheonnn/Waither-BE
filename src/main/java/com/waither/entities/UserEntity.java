package com.waither.entities;

import com.waither.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "User")
@Table(name = "User")
@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sequence")
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

    @Column(name = "status", nullable = false)
    @ColumnDefault("'A'")
    private char status;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .userName(userName)
                .email(email)
                .id(id)
                .pw(pw)
                .refreshToken(refreshToken)
                .provider(provider)
                .role(provider)
                .role(role)
                .status(status)
                .build();
    }
}
