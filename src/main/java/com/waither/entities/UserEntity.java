package com.waither.entities;

import com.waither.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

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

    //token

    @Column(name = "outTime", nullable = false)
    private Timestamp outTime;

    @Column(name = "outAlarm", nullable = false)
    private char outAlarm;

    @Column(name = "climateAlarm", nullable = false)
    private char climateAlarm;

    @Column(name = "customAlarm", nullable = false)
    private char customAlarm;

    @Column(name = "rainAlarm", nullable = false)
    private char rainAlarm;

    @Column(name = "snowAlarm", nullable = false)
    private char snowAlarm;

    @Column(name = "windAlarm", nullable = false)
    private char windAlarm;

    @Column(name = "windValue", nullable = false)
    private Integer windValue;

    @Column(name = "rainFall", nullable = false)
    private char rainFall;

    @Column(name = "dust", nullable = false)
    private char dust;

    @Column(name = "wind", nullable = false)
    private char wind;

    @Column(name = "status", nullable = false)
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
    private char Sun;

    @Column(name = "Mon", nullable = false)
    private char Mon;

    @Column(name = "Tue", nullable = false)
    private char Tue;

    @Column(name = "Wed", nullable = false)
    private char Wed;

    @Column(name = "Thu", nullable = false)
    private char Thu;

    @Column(name = "Fri", nullable = false)
    private char Fri;

    @Column(name = "Sat", nullable = false)
    private char Sat;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .userName(userName)
                .id(id)
                .pw(pw)
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
