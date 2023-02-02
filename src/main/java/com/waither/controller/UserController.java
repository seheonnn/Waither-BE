package com.waither.controller;

import com.waither.UserData;
import com.waither.mapping.MainDataMapping;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.WindAlarmMapping;
import com.waither.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.beans.HasProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Optional;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    // 7 설문 답변 저장
    @ApiOperation(value = "#7 설문 답변 저장 api" , notes = "Param에 userIdx, Body에 String:String 형식으로 type, value를 담아서 요청 ex) @Param userIdx = 1 @Body {\"type\" = \"cold\", \"value\" = \"10\"}")
    @ResponseBody
    @PostMapping("/survey")
    public ResponseEntity<Void> savedSurvey(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        if (userService.savedSurvey(userIdx, request.get("type"), Integer.valueOf(request.get("value")))) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 11 설정 메인화면 조회
    @ApiOperation( value = "#11 설정 메인화면 조회 api", notes = "Param에 userIdx 담아서 요청 ex) userIdx = 1")
    @ResponseBody
    @GetMapping("/settings")
    public ResponseEntity<Optional<MainDataMapping>> getMainData(@RequestParam("userIdx") Long userIdx) {
        Optional<MainDataMapping> mainData = userService.getMainData(userIdx);
        if (mainData.isPresent())
            return ResponseEntity.ok(mainData);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // 12 설정 메인화면 변경
    @ApiOperation(value = "#12 설정 메인화면 변경 api", notes = "Param에 userIdx, Body에 String: String으로 담아서 요청 ex) {\"rainFall\":\"Y\" ,\"dust\":\"N\",\"wind\":\"Y\"} ")
    @ResponseBody
    @PostMapping("/settings")
    public ResponseEntity<Void> updateMainData(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        if(userService.updateMainData(userIdx,request.get("rainFall").charAt(0), request.get("dust").charAt(0), request.get("wind").charAt(0))) {
            log.info(ResponseEntity.ok(null));
            return ResponseEntity.ok(null);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // 13 사용자 설정 데이터 조회
    @ApiOperation(value = "#13 사용자 설정 데이터 조회 api", notes = "Param에 userIdx 담아서 요청 ex) userIdx = 1")
    @ResponseBody
    @GetMapping("/settings/userdata")
    public ResponseEntity<UserData> getUserData(@RequestParam("userIdx") Long userIdx) {

        UserData userData = userService.getUserData(userIdx);
        if (userData != null)
            return ResponseEntity.ok(userData);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    // 14 사용자 설정 데이터 변경
    @ApiOperation( value = "#14 사용자 설정 데이터 변경 api", notes = "Param에 userIdx, Body에 String:String으로 type, value를 담아서 요청 ex) @Param userIdx = 1 @Body{\"type\": \"Y \" , \"cold\": \" N\", \"value\": \"-10\" }")
    @ResponseBody
    @PostMapping("/settings/userdata")
    public ResponseEntity<Void> updateUserData(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        if (userService.updateUserData(userIdx, request.get("type"), Integer.valueOf(request.get("value")))) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 15 사용자 알람 설정 조회
    @ApiOperation(value = "#15 사용자 알람 설정 조회 api", notes = "Param에 userIdx 담아서 요청 ex) userIdx = 1")
    @ResponseBody
    @GetMapping("/settings/alarm")
    public ResponseEntity<Optional<UserAlarmMapping>> getAlarmData(@RequestParam("userIdx") Long userIdx) {
        Optional<UserAlarmMapping> userAlarmData = userService.getAlarmData(userIdx);
        if (userAlarmData.isPresent())
            return ResponseEntity.ok(userAlarmData);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //16 알람 설정 변경
    @ApiOperation(value = "#16 알람 설정 변경 api", notes = "Param에 userIdx, Body에 String:String으로 Mon, Tue, Wed, Thu, Fri, Sat, Sun, outAlarm, climateAlarm, customAlarm, rainAlarm, snowAlarm을 담아서 요청" +
            "\nex){\"Mon\": \"Y\" , \"Tue\": \"Y\", \"Wed\": \"Y\", \"Thu\": \"Y\", \"Fri\": \"Y\", \"Sat\": \"N\", \"Sun\": \"N\", \"outAlarm\": \"Y\", \"climateAlarm\": \"Y\", \"customAlarm\": \"Y\", \"rainAlarm\": \"Y\", \"customAlarm\": \"Y\", \"snowAlarm\": \"Y\" } " +
            "\n 각 요일 : on/off, outAlarm : 외출 알람 설정 on/off, climateAlarm : 기상 예보 알람 on/off, customAlarm : 사용자 맞춤 알람 on/off, rainAlarm : 소나기 알람 받기 on/off, snowAlarm : 강설 정보 받기 on/off")
    @ResponseBody
    @PostMapping("/settings/alarm")
    public ResponseEntity<Void> updateAlarmData(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        if (userService.updateAlarmData(userIdx,
                request.get("Mon").charAt(0), request.get("Tue").charAt(0), request.get("Wed").charAt(0), request.get("Thu").charAt(0), request.get("Fri").charAt(0), request.get("Sat").charAt(0),request.get("Sun").charAt(0),
                request.get("outAlarm").charAt(0), request.get("climateAlarm").charAt(0), request.get("customAlarm").charAt(0),
                request.get("rainAlarm").charAt(0),request.get("snowAlarm").charAt(0))) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 17 사용자 바람 세기 설정 조회
    @ApiOperation(value = "#17 사용자 바람 세기 설정 조회 api", notes = "Param에 userIdx담아서 요청 ex) @Param userIdx = 1")
    @ResponseBody
    @GetMapping("/settings/alarm/wind")
    public ResponseEntity<Optional<WindAlarmMapping>> getWindAlarm(@RequestParam("userIdx") Long userIdx) {
        Optional<WindAlarmMapping> userWindAlarm = userService.getWindAlarm(userIdx);
        if (userWindAlarm.isPresent())
            return ResponseEntity.ok(userWindAlarm);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // 18 사용자 바람 세기 설정 변경
    @ApiOperation(value = "#18 사용자 바람 세기 설정 변경 api", notes = "Param에 userIdx, Body에 String:String으로 windAlarm, windValue 담아서 요청 ex) @Param userIdx = 1 @Body {\"windAlarm\": \"Y \" , \"windValue\": \"10\"}")
    @ResponseBody
    @PostMapping("/settings/alarm/wind")
    public ResponseEntity<Void> updateWindAlarm(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        if (userService.updateWindAlarm(userIdx, request.get("windAlarm").charAt(0), Integer.valueOf(request.get("windValue")))) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
