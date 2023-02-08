package com.waither.dto;

import com.waither.entities.UserDetailEntity;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDetailDTO {

    private Long userIdx;
//
//    private String userName;
//
//    private String email;
//
//    private String id;
//
//    private String pw;
//
//    private String refreshToken;
//
//    private String provider;
//
//    private String role;

    private Time outTime;

    private char outAlarm;

    private char climateAlarm;

    private char customAlarm;

    private char rainAlarm;

    private char snowAlarm;

    private char windAlarm;

    private double windValue;

    private char rainFall;

    private char dust;

    private char wind;

    private Timestamp updatedAt;

    private Timestamp createdAt;

    private Integer veryCold;

    private Integer cold;

    private Integer good;

    private Integer hot;

    private Integer veryHot;

    private char Sun;

    private char Mon;

    private char Tue;

    private char Wed;

    private char Thu;

    private char Fri;

    private char Sat;

    public UserDetailEntity toEntity() {
        return UserDetailEntity.builder()
//                .userName(userName)
//                .email(email)
//                .id(id)
//                .pw(pw)
//                .refreshToken(refreshToken)
//                .provider(provider)
//                .role(role)
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
