package com.waither.controller;

import com.waither.model.ServicesResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("")
public class SettingController {

    ServicesResponse servicesResponse;

    // #19 유저 정보 조회
    @GetMapping("/settings/user")
    public ServicesResponse userInfo(){
        return servicesResponse;
    }

    // #20 유저 이름 변경
    @PostMapping("/settings/user")
    public ServicesResponse userNameChange(){
        return servicesResponse;
    }

    // #21 비밀번호 일치 확인
    @PostMapping("/settings/user/password")
    public ServicesResponse verifyPassword(){
        return servicesResponse;
    }

    // #22 비밀번호 재설정
    @PostMapping("/settings/user/password/change")
    public ServicesResponse changePassword(){
        return servicesResponse;
    }

    // #23 유저 로그아웃
    @PutMapping("/settings/user")
    public ServicesResponse userLogout(){
        return servicesResponse;
    }

    // #24 회원 탈퇴
    @DeleteMapping("/settings/user")
    public ServicesResponse userWithdraw(){
        return servicesResponse;
    }
}
