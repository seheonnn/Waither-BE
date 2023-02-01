package com.waither.entities;

import com.waither.dto.UserDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sequence")
    private Long userIdx;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "authId", nullable = true)
    private String authId;

    @Column(name = "pw", nullable = true)
    private String pw;

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
                .authId(authId)
                .pw(pw)
                .provider(provider)
                .role(provider)
                .status(status)
                .build();
    }
}
