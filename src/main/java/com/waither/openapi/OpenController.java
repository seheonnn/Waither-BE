package com.waither.openapi;

import com.waither.config.BaseException;
import com.waither.config.BaseResponse;
import com.waither.config.BaseResponseStatus.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.waither.openapi.model.*;

import java.util.HashMap;
import java.util.Map;

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

    GpsTransfer gpt = new GpsTransfer();
    RegionTransfer rpt = new RegionTransfer();


    /*메인화면에 필요한 데이터 반환
    요청: x, y 좌표
    응답: GetWeatherRes
     */
    @GetMapping("/")
    public BaseResponse<GetWeatherRes> getWeather(@RequestParam("x") String x, @RequestParam("y") String y) throws Exception{
        try {
            //log.info("startttt");
            //위경도값 좌표로 변환
            gpt.setLat(Double.parseDouble(x));
            gpt.setLon(Double.parseDouble(y));
            gpt.transfer(gpt,0);
            String nx = String.valueOf((int)gpt.getxLat());
            String ny = String.valueOf((int)gpt.getyLon());
            //
            GetWeatherRes getWeatherRes = openProvider.getMainWea(nx,ny);
            return new BaseResponse<>(getWeatherRes);
        } catch (BaseException exception) {
            //log.info("star");
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*설문데이터 저장할때 필요한 데이터 반환
    요청: 날짜, 시간, 지역이름
    응답: 기온 */
    public GetPastWeatherRes getPastWeather(String date, String time, String region) throws Exception{
        try {
            int re = rpt.get(region);
            String reg = String.valueOf(re);
            GetPastWeatherRes getPastWeatherRes = openProvider.getPastWea(date, time, reg);
            //GetPastWeatherRes getPastWeatherRes = openProvider.getPastWea(date, time, region);
            return getPastWeatherRes;
        } catch (BaseException exception) {
            GetPastWeatherRes getPastWeatherRes = new GetPastWeatherRes();
            return getPastWeatherRes;
        }
    }

    /*@GetMapping("/past/")
    public GetPastWeatherRes getPastWeather(@RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("region") String region) throws Exception{
        try {
            int re = rpt.get(region);
            String reg = String.valueOf(re);
            GetPastWeatherRes getPastWeatherRes = openProvider.getPastWea(date, time, reg);
            //GetPastWeatherRes getPastWeatherRes = openProvider.getPastWea(date, time, region);
            return getPastWeatherRes;
        } catch (BaseException exception) {
            GetPastWeatherRes getPastWeatherRes = new GetPastWeatherRes();
            return getPastWeatherRes;
        }
    }*/

}
