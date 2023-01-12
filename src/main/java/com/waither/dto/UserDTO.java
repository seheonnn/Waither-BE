package com.waither.dto;

import com.waither.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    private Integer userIdx;

    private String userName;

    private String id;

    private String pw;

    //token

    private Timestamp outTime;

    private char outAlarm;

    private char climateAlarm;

    private char customAlarm;

    private char rainAlarm;

    private char snowAlarm;

    private char windAlarm;

    private Integer windDegree;

    private char rainFall;

    private char dust;

    private char wind;

    private char status;

    private Timestamp updatedAt;

    private Timestamp createdAt;

    private double veryCold;

    private double cold;

    private double good;

    private double hot;

    private double veryHot;

    private char Sun;

    private char Mon;

    private char Tue;

    private char Wed;

    private char Thu;

    private char Fri;

    private char Sat;

    public UserEntity toEntity() {
        return UserEntity.builder()
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
                .windDegree(windDegree)
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
