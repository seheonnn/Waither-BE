package com.waither.openapi;

import com.waither.config.BaseException;
import com.waither.config.BaseResponse;
import com.waither.config.BaseResponseStatus.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.waither.openapi.model.*;

@RestController
@RequestMapping("/app/main")
public class OpenController {
    @Autowired
    private final OpenProvider openProvider;

    public OpenController(OpenProvider openProvider) {
        this.openProvider = openProvider;
    }

    @ResponseBody
    @GetMapping("/")
    /*public BaseResponse<GetWeatherRes> getWeather(@RequestParam(required = false) String x, @RequestParam(required = false) String y) throws Exception{
        try {
            GetWeatherRes getWeatherRes = openProvider.getUltraSc(x,y);
            return new BaseResponse<>(getWeatherRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }*/
    public String getWeather(@RequestParam("x") String x, @RequestParam("y") String y) throws Exception{
        try {
            GetWeatherRes getWeatherRes = openProvider.getUltraSc(x,y);
            return "d";
            //return new BaseResponse<>(getWeatherRes);
        } catch (BaseException exception) {
            return "dd";
            //return new BaseResponse<>((exception.getStatus()));
        }
    }

}
