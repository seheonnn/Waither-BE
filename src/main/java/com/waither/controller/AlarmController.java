package com.waither.controller;

import com.waither.config.BaseException;
import com.waither.config.BaseResponse;
import com.waither.config.BaseResponseStatus;
import com.waither.entities.AlarmEntity;
import com.waither.openapi.OpenProvider;
import com.waither.openapi.model.GetWeatherRes;
import com.waither.openapi.model.GpsTransfer;
import com.waither.service.AlarmService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/alarm")
public class AlarmController {

    @Autowired
    AlarmService alarmService;

    @Autowired
    private final OpenProvider openProvider;

    GpsTransfer gpt = new GpsTransfer();

    // 알람 생성 - 정기
    @ApiOperation(value = "정기 알람 생성 api", notes = "Param x, y 에 각각 위도, 경도 받아서 알람 생성 ex) @Param x = 36 y = 127")
    @PostMapping("/create")
    public BaseResponse<GetWeatherRes> createAlarm(@RequestParam("x") String x, @RequestParam("y") String y) throws Exception{
        try {
            //위경도값 좌표로 변환
            gpt.setLat(Double.parseDouble(x));
            gpt.setLon(Double.parseDouble(y));
            gpt.transfer(gpt,0);
            String nx = String.valueOf((int)gpt.getxLat());
            String ny = String.valueOf((int)gpt.getyLon());
            //
            GetWeatherRes getWeatherRes = openProvider.getMainWea(nx,ny);

            Long userIdx = 1L;

            //기후 알람
            String climateAlarmContents = alarmService.createClimateAlarm(userIdx);

            //소나기 알람
            String rainAlarmContents = alarmService.createRainAlarm(userIdx, getWeatherRes.getTime(), getWeatherRes.getExpect_rn1(), getWeatherRes.getExpect_rn2(), getWeatherRes.getExpect_rn3(), getWeatherRes.getExpect_rn4(), getWeatherRes.getExpect_rn5(), getWeatherRes.getExpect_rn6());

            //강설 알람
            String snowAlarmContents = alarmService.createSnowAlarm(userIdx);

            //설정 풍속 알람
            String windAlarmContents = alarmService.createWindAlarm(userIdx, getWeatherRes.getWsd());

            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 알람 생성 - 외출 전
    @ApiOperation(value = "외출 전 알람 생성 api", notes = "Param x, y 에 각각 위도, 경도 받아서 알람 생성 ex) @Param x = 36 y = 127")
    @PostMapping("/create/day")
    public BaseResponse<GetWeatherRes> createDayAlarm(@RequestParam("x") String x, @RequestParam("y") String y) throws Exception{
        try {
            //위경도값 좌표로 변환
            gpt.setLat(Double.parseDouble(x));
            gpt.setLon(Double.parseDouble(y));
            gpt.transfer(gpt,0);
            String nx = String.valueOf((int)gpt.getxLat());
            String ny = String.valueOf((int)gpt.getyLon());
            //
            GetWeatherRes getWeatherRes = openProvider.getMainWea(nx,ny);

            Long userIdx = 1L;

            // --------------------알람 생성
            String tempAlarmContents = alarmService.createTempAlarm(userIdx, getWeatherRes.getTmp());

            //외출 전 *
            String outAlarmContents = alarmService.createOutAlarm(userIdx, getWeatherRes);

            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 9 전체 알람 조회
    @ApiOperation(value = "#9 전체 알람 조회 api", notes = "Param에 userIdx담아서 요청 ex) @Param userIdx = 1")
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<AlarmEntity>> myAlarmList(@RequestParam("userIdx") Long userIdx) {
        try{
            List<AlarmEntity> list = alarmService.myAlarmList(userIdx);
            return new BaseResponse<>(list);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 10 알람 삭제
    @ApiOperation(value = "#10 알람 삭제 api", notes = "Param에 userIdx, Body에 String:String으로 alarmIdx를 담아서 요청 ex) @Param userIdx = 1 @Body {\"alarmIdx\":\"1\"}")
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Void> deleteAlarm(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) throws Exception{
        try{
            alarmService.delete(Long.valueOf(request.get("alarmIdx")));
            return new BaseResponse<>();
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
