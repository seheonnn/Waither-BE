package com.waither.controller;

import com.waither.dto.UserDTO;
import com.waither.entities.UserEntity;
import com.waither.mapping.MainDataMapping;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.UserDataMapping;
import com.waither.mapping.WindAlarmMapping;
import com.waither.repository.UserRepository;
import com.waither.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;
    private final UserRepository userRepository;

    // 7 설문 답변 저장
    @ResponseBody
    @PostMapping("/survey")
    public ResponseEntity<Void> savedSurvey(@RequestParam("userIdx") Long userIdx, @RequestBody String type, Double value) {
        if (userService.savedSurvey(userIdx, type, value)) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 11 설정 메인화면 조회
    @ResponseBody
    @GetMapping("/settings")
    public ResponseEntity<Optional<MainDataMapping>> getMainData(@RequestParam("userIdx") Long userIdx) {
        try {
            Optional<MainDataMapping> mainData = userService.getMainData(userIdx);
            return ResponseEntity.ok(mainData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 12 설정 메인화면 변경
    @ResponseBody
    @PostMapping("/settings")
    public ResponseEntity<Void> updateMainData(@RequestParam("userIdx") Long userIdx, @RequestBody char rainFall, char dust, char wind) {
        if(userService.updateMainData(userIdx, rainFall, dust, wind))
            return ResponseEntity.ok(null);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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

    // 17 사용자 바람 세기 설정 조회
    @ResponseBody
    @GetMapping("/settings/alarm/wind")
    public ResponseEntity<Optional<WindAlarmMapping>> getWindAlarm(@RequestParam("userIdx") Long userIdx) {
        try {
            Optional<WindAlarmMapping> userWindAlarm = userService.getWindAlarm(userIdx);
            return ResponseEntity.ok(userWindAlarm);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 18 사용자 바람 세기 설정 변경
    @ResponseBody
    @PostMapping("/settings/alarm/wind")
    public ResponseEntity<Void> updateWindAlarm(@RequestParam("userIdx") Long userIdx, @RequestBody Character windAlarm, Integer windValue) {
        if (userService.updateWindAlarm(userIdx, windAlarm, windValue)) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
