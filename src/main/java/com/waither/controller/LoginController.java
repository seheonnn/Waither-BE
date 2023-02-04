package com.waither.controller;

import com.waither.config.BaseException;
import com.waither.config.BaseResponse;
import com.waither.config.BaseResponseStatus;
import com.waither.service.EmailService;
import com.waither.service.LoginService;
import com.waither.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;

    //#1 유저 일반 로그인
    @ApiOperation(value = "#1 유저 일반 로그인", notes = "Body에 email, pw 담아서 보내기 ex) { \"email\" : \"abc123@gmail.com\", \"pw\": \"abc1234\" }")
    @PostMapping("")
    public BaseResponse<Void> userLogin(@RequestBody HashMap<String,String> request) throws Exception{
        try{
            loginService.userLogin(request.get("email"), request.get("pw"));
            return new  BaseResponse<>(null);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    //#2 비밀번호 찾기
    @ApiOperation(value = "#2 비밀번호 찾기", notes = "Param에 유저가 입력한 이메일 보내기 ex) email = \"abc1234\"")
    @PostMapping("/password")
    public BaseResponse<Void> emailConfirm(@RequestParam String email) throws Exception {
        if(userService.emailValidation(email)){
            try{
                String ePw = emailService.sendSimpleMessage(email);
                userService.updatePw(userService.findUserByEmail(email), ePw);
                return new BaseResponse<>(null);
            } catch (BaseException exception){
                return new BaseResponse<>(exception.getStatus());
            }
        } else return new BaseResponse<>(BaseResponseStatus.INVALID_EMAIL);
    }
}
