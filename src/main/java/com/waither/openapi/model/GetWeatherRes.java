package com.waither.openapi.model;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;




@Getter
@Setter
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname, email, password)를 받는 생성자를 생성

public class GetWeatherRes {
    private String date;
    private String time;
    private double tmp;
    private int tmn;
    private int tmx;
    private double rn1;
    private int vec;
    private double wsd;
    private double reh;
    private int pm10_value;
    private String pm10_grade;

    private double expect_tmp1;
    private double expect_tmp2;
    private double expect_tmp3;
    private double expect_tmp4;
    private double expect_tmp5;
    private double expect_tmp6;

    private String expect_rn1;
    private String expect_rn2;
    private String expect_rn3;
    private String expect_rn4;
    private String expect_rn5;
    private String expect_rn6;

    //하늘상태(맑음, 구름많음, 흐림)
    private String expect_sky1;
    private String expect_sky2;
    private String expect_sky3;
    private String expect_sky4;
    private String expect_sky5;
    private String expect_sky6;


    public GetWeatherRes() {

    }

}
