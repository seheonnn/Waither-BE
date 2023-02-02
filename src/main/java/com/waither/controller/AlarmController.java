package com.waither.controller;

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
    public ResponseEntity<Map<String, Object>> myAlarmList(@RequestParam("userIdx") Long userIdx) {

        List<AlarmEntity> list = alarmService.myAlarmList(userIdx);
        Map<String, Object> map = new HashMap<>();
        map.put("alarmlist", list);
        if (map.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else
            return ResponseEntity.ok(map);
    }


    // 10 알람 삭제
    @ApiOperation(value = "#10 알람 삭제 api", notes = "Param에 userIdx, Body에 String:String으로 alarmIdx를 담아서 요청 ex) @Param userIdx = 1 @Body {\"alarmIdx\": \"1\"}")
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Void> deleteAlarm(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        if (alarmService.delete(Long.valueOf(request.get("alarmIdx")))  ) {
            return ResponseEntity.ok(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
