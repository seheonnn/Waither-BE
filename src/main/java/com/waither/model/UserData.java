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

    private int veryColdMode;
    private int vC_p;
    private int coldMode;
    private int c_p;
    private int goodMode;
    private int g_p;
    private int hotMode;
    private int h_p;
    private int veryHotMode;
    private int vH_p;

}
