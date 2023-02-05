package com.waither.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {

    private Integer veryCold;
    private Integer cold;
    private Integer good;
    private Integer hot;
    private Integer veryHot;

    private int avgVC;
    private int avgC;
    private int avgG;
    private int avgH;
    private int avgVH;

}
