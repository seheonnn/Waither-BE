package com.waither.openapi;

import com.waither.config.BaseException;
import com.waither.openapi.model.GetPastWeatherRes;
import com.waither.openapi.model.RegionTransfer;

public class getPastWeather {
    private final OpenDao openDao;
    RegionTransfer rpt = new RegionTransfer();

    public getPastWeather(OpenDao openDao) {
        this.openDao = openDao;
    }

    public GetPastWeatherRes getPastWeather(String date, String time, String region) throws Exception{
        try {
            int re = rpt.get(region);
            String reg = String.valueOf(re);
            GetPastWeatherRes getPastWeatherRes = openDao.getPastWea(date, time, reg);
            return getPastWeatherRes;
        } catch (BaseException exception) {
            GetPastWeatherRes getPastWeatherRes = new GetPastWeatherRes();
            return getPastWeatherRes;
        }
    }
}
