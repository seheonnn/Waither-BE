package com.waither.entities;

import com.waither.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Entity(name = "User")
@Table(name = "User")
@Getter
@Setter
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

    @Column(name = "outTime", nullable = false)
    private Timestamp outTime;

    @Column(name = "outAlarm", nullable = false)
    @ColumnDefault("'Y'")
    private char outAlarm;

    @Column(name = "climateAlarm", nullable = false)
    @ColumnDefault("'Y'")
    private char climateAlarm;

    @Column(name = "customAlarm", nullable = false)
    @ColumnDefault("'Y'")
    private char customAlarm;

    @Column(name = "rainAlarm", nullable = false)
    @ColumnDefault("'Y'")
    private char rainAlarm;

    @Column(name = "snowAlarm", nullable = false)
    @ColumnDefault("'Y'")
    private char snowAlarm;

    @Column(name = "windAlarm", nullable = false)
    @ColumnDefault("'Y'")
    private char windAlarm;

    @Column(name = "windValue", nullable = false)
    private Integer windValue;

    @Column(name = "rainFall", nullable = false)
    @ColumnDefault("'Y'")
    private char rainFall;

    @Column(name = "dust", nullable = false)
    @ColumnDefault("'Y'")
    private char dust;

    @Column(name = "wind", nullable = false)
    @ColumnDefault("'Y'")
    private char wind;

    @Column(name = "status", nullable = false)
    @ColumnDefault("'A'")
    private char status;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;

    @Column(name = "veryCold", nullable = false)
    private double veryCold;

    @Column(name = "cold", nullable = false)
    private double cold;

    @Column(name = "good", nullable = false)
    private double good;

    @Column(name = "hot", nullable = false)
    private double hot;

    @Column(name = "veryHot", nullable = false)
    private double veryHot;

    @Column(name = "Sun", nullable = false)
    @ColumnDefault("'Y'")
    private char Sun;

    @Column(name = "Mon", nullable = false)
    @ColumnDefault("'Y'")
    private char Mon;

    @Column(name = "Tue", nullable = false)
    @ColumnDefault("'Y'")
    private char Tue;

    @Column(name = "Wed", nullable = false)
    @ColumnDefault("'Y'")
    private char Wed;

    @Column(name = "Thu", nullable = false)
    @ColumnDefault("'Y'")
    private char Thu;

    @Column(name = "Fri", nullable = false)
    @ColumnDefault("'Y'")
    private char Fri;

    @Column(name = "Sat", nullable = false)
    @ColumnDefault("'Y'")
    private char Sat;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .userName(userName)
                .id(id)
                .pw(pw)
                .refreshToken(refreshToken)
                .provider(provider)
                .role(provider)
                .outTime(outTime)
                .outAlarm(outAlarm)
                .climateAlarm(climateAlarm)
                .customAlarm(customAlarm)
                .rainAlarm(rainAlarm)
                .snowAlarm(snowAlarm)
                .windAlarm(windAlarm)
                .windValue(windValue)
                .rainFall(rainFall)
                .dust(dust)
                .wind(wind)
                .status(status)
                .updatedAt(updatedAt)
                .createdAt(createdAt)
                .veryCold(veryCold)
                .cold(cold)
                .good(good)
                .hot(hot)
                .veryHot(veryHot)
                .Sun(Sun)
                .Mon(Mon)
                .Tue(Tue)
                .Wed(Wed)
                .Thu(Thu)
                .Fri(Fri)
                .Sat(Sat)
                .build();
    }

}
