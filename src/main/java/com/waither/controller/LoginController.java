package com.waither.controller;

import com.waither.domain.JsonResponse;
import com.waither.model.ServicesResponse;
import com.waither.model.TokenResponse;
import com.waither.security.jwt.JwtTokenProvider;
import com.waither.service.EmailService;
import com.waither.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("/app/login")
public class LoginController {

    @Autowired
    LoginServiceImpl userService;
    ServicesResponse servicesResponse;
    EmailService emailService;

    // #1 유저 일반 로그인
    @PostMapping("/login")
    public void login(HttpServletRequest request){
        request.getHeader("accessToken");
    }

    // #2 유저 비밀번호 찾기
    @PostMapping("/login/password")
    public void findPassword(@RequestBody String email){

    }

    // #5 이메일로 계속하기
    @PostMapping("/auth/email")
    public String emailConfirm(@RequestBody String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        return confirm;
    }

    // # 5-1 이메일 중복 검사
    @PostMapping("/email/duplicate")
    public void userRegister(String email){

    }

    // #6 로그인 없이 진행하기
    @PostMapping("/general")
    public void noAuthUserRegister(){

    }


}

