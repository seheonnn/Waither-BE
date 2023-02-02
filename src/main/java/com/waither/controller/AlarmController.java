package com.waither.controller;

import com.waither.config.BaseException;
import com.waither.config.BaseResponse;
import com.waither.entities.AlarmEntity;
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

    // 8 알람 생성
//    @ResponseBody
//    @GetMapping("main")
//    public ResponseEntity<String> createAlarm (@RequestParam("userIdx") Long userIdx                           ) {

//        String tempAlarm = alarmService.createTempAlarm(userIdx,         );
//        String outAlarm = alarmService.createOutAlarm(userIdx,         );
//        String climateAlarm = alarmService.createClimateAlarm(userIdx, );
//        String customAlarm = alarmService.createCustomAlarm(userIdx, );
//        String rainAlarm = alarmService.createRainAlarm(userIdx, );
//        String snowAlarm = alarmService.createSnowAlarm(userIdx, );
//        String windAlarm = alarmService.createWindAlarm(userIdx, windValue);

        //
//    }

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
            return new BaseResponse<>(null);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
