package com.waither.openapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetPastWeatherRes {
    private double tmp;
//    private double vec;
//    private double reh;

    public GetPastWeatherRes() {

    }
}
