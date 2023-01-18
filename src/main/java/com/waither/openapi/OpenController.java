package com.waither.openapi;

import com.waither.config.BaseException;
import com.waither.config.BaseResponse;
import com.waither.config.BaseResponseStatus.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.waither.openapi.model.*;

@Log4j2
@RestController
@RequestMapping("/app/main")
@ResponseBody
public class OpenController {
    @Autowired
    private final OpenProvider openProvider;

    public OpenController(OpenProvider openProvider) {
        this.openProvider = openProvider;
    }

    @GetMapping("/")
    public BaseResponse<GetWeatherRes> getWeather(@RequestParam("x") String x, @RequestParam("y") String y) throws Exception{
        try {
            log.info("startttt");
            GetWeatherRes getWeatherRes = openProvider.getUltraSc(x,y);
            return new BaseResponse<>(getWeatherRes);
        } catch (BaseException exception) {
            log.info("star");
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
