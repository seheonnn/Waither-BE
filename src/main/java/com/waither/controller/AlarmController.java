package com.waither.controller;

import com.waither.dto.UserDTO;
import com.waither.entities.AlarmEntity;
import com.waither.service.AlarmService;
import com.waither.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

    // 9 전체 알람 조회
    @ResponseBody
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> myAlarmList(@RequestParam("userIdx") Long userIdx) {
        try {
            List<AlarmEntity> list = alarmService.myAlarmList(userIdx);
            Map<String, Object> map = new HashMap<>();
            map.put("alarmlist", list);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    // 10 알람 삭제
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Void> deleteAlarm(@RequestParam("alarmIdx") Long alarmIdx) {
        if (alarmService.delete(alarmIdx)) {
            return ResponseEntity.ok(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
