package com.waither.controller;

import com.waither.model.ServicesResponse;
import com.waither.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("")
public class SettingController {

    EmailService emailService;

    // #19 유저 정보 조회
    @GetMapping("/settings/user")
    public void userInfo( @RequestHeader String accessToken){
        return;
    }

    // #20 유저 이름 변경
    @PostMapping("/settings/user")
    public void userNameChange(){

    }

    // #21 비밀번호 일치 확인
    @PostMapping("/settings/user/password")
    public void verifyPassword(){

    }

    /**
     * 이메일은 하루에 500개 제한
     */
    // #22 비밀번호 재설정
    @PostMapping("/settings/user/password/change")
    public void changePassword(HttpServletRequest request) throws Exception {
        emailService.sendPassword(request.getParameter("email"));
    }

    // #23 유저 로그아웃
    @PutMapping("/settings/user")
    public void userLogout(){
    }

    // #24 회원 탈퇴
    @DeleteMapping("/settings/user")
    public void userWithdraw(){

    }
}
