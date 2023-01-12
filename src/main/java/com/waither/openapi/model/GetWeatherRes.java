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
    private double tmn;
    private double tmx;
    private double rn1;
    private double vec;
    private double wsd;
    private double reh;
    private int pm10_value;
    private int pm10_grade;

    private double expect_tmp1;
    private double expect_tmp2;
    private double expect_tmp3;
    private double expect_tmp4;
    private double expect_tmp5;
    private double expect_tmp6;
    private double expect_rn1;
    private double expect_rn2;
    private double expect_rn3;
    private double expect_rn4;
    private double expect_rn5;
    private double expect_rn6;


    public GetWeatherRes() {

    }
    public GetWeatherRes(String date, String time, double tmp, double tmn, double tmx, double rn1, int vec, int wsd, int reh, int pm10_value, int pm10_grade,
                         double expect_tmp1, double expect_tmp2, double expect_tmp3, double expect_tmp4, double expect_tmp5, double expect_tmp6,
                         double expect_rn1, double expect_rn2, double expect_rn3, double expect_rn4, double expect_rn5, double expect_rn6) {
        this.date=date;
        this.time=time;
        this.tmp=tmp;
        this.tmn=tmn;
        this.tmx=tmx;
        this.rn1=rn1;
        this.vec=vec;
        this.wsd=wsd;
        this.reh=reh;
        this.pm10_value=pm10_value;
        this.pm10_grade=pm10_grade;

        this.expect_tmp1=expect_tmp1;
        this.expect_tmp2=expect_tmp2;
        this.expect_tmp3=expect_tmp3;
        this.expect_tmp4=expect_tmp4;
        this.expect_tmp5=expect_tmp5;
        this.expect_tmp6=expect_tmp6;
        this.expect_rn1=expect_rn1;
        this.expect_rn2=expect_rn2;
        this.expect_rn3=expect_rn3;
        this.expect_rn4=expect_rn4;
        this.expect_rn5=expect_rn5;
        this.expect_rn6=expect_rn6;
    }

}
