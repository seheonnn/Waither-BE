package com.waither.controller;

import com.waither.dto.UserDTO;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.UserDataMapping;
import com.waither.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    // 7 설문 답변 저장
    @ResponseBody
    @PostMapping("/survey")
    public ResponseEntity<Void> savedSurvey(@RequestBody Long userIdx, double veryCold, double cold, double good, double hot, double veryHot, Timestamp outTime) {
        if (userService.savedSurvey(userIdx, veryCold, cold, good, hot, veryHot, outTime)) {
            return ResponseEntity.ok(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 13 사용자 설정 데이터 조회
    @ResponseBody
    @GetMapping("/settings/userdata")
    public ResponseEntity<Optional<UserDataMapping>> getUserData(@RequestParam("userIdx") Long userIdx) {
        try {
            Optional<UserDataMapping> userData = userService.getUserData(userIdx);
            return ResponseEntity.ok(userData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 14 사용자 설정 데이터 변경
    @ResponseBody
    @PostMapping("/settings/userdata")
    public ResponseEntity<Void> updateUserData(@RequestParam("userIdx") Long userIdx, @RequestBody String type, Double value) {
        if (userService.updateUserData(userIdx, type, value)) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 15 사용자 알람 설정 조회
    @ResponseBody
    @GetMapping("/settings/alarm")
    public ResponseEntity<Optional<UserAlarmMapping>> getAlarmData(@RequestParam("userIdx") Long userIdx) {
        try {
            Optional<UserAlarmMapping> userAlarmData = userService.getAlarmData(userIdx);
            return ResponseEntity.ok(userAlarmData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //16 알람 설정 변경
    @ResponseBody
    @PostMapping("/settings/alarm")
    public ResponseEntity<Void> updateAlarmData(@RequestParam("userIdx") Long userIdx, @RequestBody Character Mon, Character Tue, Character Wed, Character Thu, Character Fri, Character Sat, Character Sun, Character outAlarm, Character climateAlarm, Character customAlarm, Character rainAlarm, Character snowAlarm) {
        if (userService.updateAlarmData(userIdx, Mon, Tue, Wed, Thu, Fri, Sat, Sun, outAlarm, climateAlarm, customAlarm, rainAlarm, snowAlarm)) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
