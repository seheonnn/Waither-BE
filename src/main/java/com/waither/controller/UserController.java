package com.waither.controller;

import com.waither.config.BaseResponseStatus;
import com.waither.model.UserData;
import com.waither.config.BaseException;
import com.waither.config.BaseResponse;
import com.waither.mapping.MainDataMapping;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.WindAlarmMapping;
import com.waither.model.UserInfo;
import com.waither.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    // 7 설문 답변 저장
    @ApiOperation(value = "#7 설문 답변 저장 api", notes = "Param에 userIdx, Body에 String:String 형식으로 type, value를 담아서 요청 ex) @Param userIdx = 1 @Body {\"type\" : \"cold\", \"value\" : \"10\"}")
    @ResponseBody
    @PostMapping("/survey")
    public BaseResponse<Void> savedSurvey(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) throws Exception {
        try {
            userService.savedSurvey(userIdx, request.get("type"), Integer.valueOf(request.get("value")));
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 11 설정 메인화면 조회
    @ApiOperation(value = "#11 설정 메인화면 조회 api", notes = "Param에 userIdx 담아서 요청 ex) userIdx = 1")
    @ResponseBody
    @GetMapping("/settings")
    public BaseResponse<Optional<MainDataMapping>> getMainData(@RequestParam("userIdx") Long userIdx) throws Exception {
        try {
            Optional<MainDataMapping> mainData = userService.getMainData(userIdx);
            assert mainData.isPresent();
            return new BaseResponse<>(mainData);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 12 설정 메인화면 변경
    @ApiOperation(value = "#12 설정 메인화면 변경 api", notes = "Param에 userIdx, Body에 String: String으로 담아서 요청 ex) {\"rainFall\":\"Y\" ,\"dust\":\"N\",\"wind\":\"Y\"} ")
    @ResponseBody
    @PostMapping("/settings")
    public BaseResponse<Void> updateMainData(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        try {
            userService.updateMainData(userIdx, request.get("rainFall").charAt(0), request.get("dust").charAt(0), request.get("wind").charAt(0));
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 13 사용자 설정 데이터 조회
    @ApiOperation(value = "#13 사용자 설정 데이터 조회 api", notes = "Param에 userIdx 담아서 요청 ex) userIdx = 1")
    @ResponseBody
    @GetMapping("/settings/userdata")
    public BaseResponse<UserData> getUserData(@RequestParam("userIdx") Long userIdx) {
        try {
            UserData userData = userService.getUserData(userIdx);
            return new BaseResponse<>(userData);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 14 사용자 설정 데이터 변경
    @ApiOperation(value = "#14 사용자 설정 데이터 변경 api", notes = "Param에 userIdx, Body에 String:String으로 type, value를 담아서 요청 ex) @Param userIdx = 1 @Body{\"type\": \"cold\", \"value\": \"-10\" }")
    @ResponseBody
    @PostMapping("/settings/userdata")
    public BaseResponse<Void> updateUserData(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        try {
            userService.updateUserData(userIdx, request.get("type"), Integer.valueOf(request.get("value")));
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 15 사용자 알람 설정 조회
    @ApiOperation(value = "#15 사용자 알람 설정 조회 api", notes = "Param에 userIdx 담아서 요청 ex) userIdx = 1")
    @ResponseBody
    @GetMapping("/settings/alarm")
    public BaseResponse<Optional<UserAlarmMapping>> getAlarmData(@RequestParam("userIdx") Long userIdx) {
        try {
            Optional<UserAlarmMapping> userAlarmData = userService.getAlarmData(userIdx);
            assert userAlarmData.isPresent();
            return new BaseResponse<>(userAlarmData);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //16 알람 설정 변경
    @ApiOperation(value = "#16 알람 설정 변경 api", notes = "Param에 userIdx, Body에 String:String으로 Mon, Tue, Wed, Thu, Fri, Sat, Sun, outTime ,outAlarm, climateAlarm, customAlarm, rainAlarm, snowAlarm을 담아서 요청" +
            "\nex){\"Mon\": \"Y\" , \"Tue\": \"Y\", \"Wed\": \"Y\", \"Thu\": \"Y\", \"Fri\": \"Y\", \"Sat\": \"N\", \"Sun\": \"N\",\"outTime\":\"08:00:00\" ,\"outAlarm\": \"Y\", \"climateAlarm\": \"Y\", \"customAlarm\": \"Y\", \"rainAlarm\": \"Y\", \"customAlarm\": \"Y\", \"snowAlarm\": \"Y\" } " +
            "\n 각 요일 : on/off, outAlarm : 외출 알람 설정 on/off, climateAlarm : 기상 예보 알람 on/off, customAlarm : 사용자 맞춤 알람 on/off, rainAlarm : 소나기 알람 받기 on/off, snowAlarm : 강설 정보 받기 on/off")
    @ResponseBody
    @PostMapping("/settings/alarm")
    public BaseResponse<Void> updateAlarmData(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        try {
            userService.updateAlarmData(userIdx,
                    request.get("Mon").charAt(0), request.get("Tue").charAt(0), request.get("Wed").charAt(0), request.get("Thu").charAt(0), request.get("Fri").charAt(0), request.get("Sat").charAt(0), request.get("Sun").charAt(0),
                    request.get("outTime"),request.get("outAlarm").charAt(0), request.get("climateAlarm").charAt(0), request.get("customAlarm").charAt(0),
                    request.get("rainAlarm").charAt(0), request.get("snowAlarm").charAt(0));
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 17 사용자 바람 세기 설정 조회
    @ApiOperation(value = "#17 사용자 바람 세기 설정 조회 api", notes = "Param에 userIdx담아서 요청 ex) @Param userIdx = 1")
    @ResponseBody
    @GetMapping("/settings/alarm/wind")
    public BaseResponse<Optional<WindAlarmMapping>> getWindAlarm(@RequestParam("userIdx") Long userIdx) {
        try {
            Optional<WindAlarmMapping> userWindAlarm = userService.getWindAlarm(userIdx);
            assert userWindAlarm.isPresent();
            return new BaseResponse<>(userWindAlarm);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 18 사용자 바람 세기 설정 변경
    @ApiOperation(value = "#18 사용자 바람 세기 설정 변경 api", notes = "Param에 userIdx, Body에 String:String으로 windAlarm, windValue 담아서 요청 ex) @Param userIdx = 1 @Body {\"windAlarm\": \"Y \" , \"windValue\": \"0\"}")
    @ResponseBody
    @PostMapping("/settings/alarm/wind")
    public BaseResponse<Void> updateWindAlarm(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) {
        try {
            userService.updateWindAlarm(userIdx, request.get("windAlarm").charAt(0), Integer.valueOf(request.get("windValue")));
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //#19 회원정보 조회
    @ApiOperation(value = "#19 회원 정보 조회 api", notes = "Param에 userIdx 담아서 요청 ex) userIdx = 1")
    @ResponseBody
    @GetMapping("/settings/user")
    public BaseResponse<UserInfo> getUserInfo(@RequestParam("userIdx") Long userIdx) throws Exception {
        try {
            UserInfo userInfo = userService.getUserInfo(userIdx);
            return new BaseResponse<>(userInfo);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //#20 회원이름 변경
    @ApiOperation(value = "#20 회원 이름 변경 api", notes = "Param에 userIdx, Body에 String:String으로 name:변경할 이름 담아서 요청 ex) {\"name\" : \"동동키\"} ")
    @ResponseBody
    @PostMapping("/settings/user")
    public BaseResponse<UserInfo> updateUserName(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) throws Exception {
        try {
            userService.updateUserName(userIdx, request.get("name"));
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    //#21 비밀번호 재설정 - 일치 확인
    @ApiOperation(value = "#21 비밀번호 일치 확인 api", notes = "Param에 userIdx, Body에 String:String으로 password 담아서 요청 ex) {\"password\" : \"abc123\"} \n 테스트 시 raw데이터 보내기.")
    @ResponseBody
    @PostMapping("/settings/user/password")
    public BaseResponse<String> pwValidation(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) throws Exception {
            if(userService.pwValidation(userIdx,request.get("password"))){
                return new BaseResponse<>("일치 확인");
            }else return new BaseResponse<>(BaseResponseStatus.INVALID);
    }

    //#22 비밀번호 재설정
    @ApiOperation(value = "#22 비밀번호 재설정 api", notes = "Param에 userIdx, Body에 String:String으로 password 담아서 요청 ex) {\"password\" : \"abc1234\"} \n 테스트 시 raw데이터 보내기.")
    @ResponseBody
    @PostMapping("/settings/user/password/change")
    public BaseResponse<Void> updatePw(@RequestParam("userIdx") Long userIdx, @RequestBody HashMap<String, String> request) throws Exception {
        try {
            userService.updatePw(userIdx,request.get("password"));
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
