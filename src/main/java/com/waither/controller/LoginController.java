package com.waither.controller;


import com.waither.domain.JsonResponse;
import com.waither.dto.OAuthDto;
import com.waither.security.oauth.service.OAuthService;
import com.waither.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.waither.config.BaseException;
import com.waither.config.BaseResponse;
import com.waither.config.BaseResponseStatus;
import com.waither.service.EmailService;
import com.waither.service.LoginService;
import com.waither.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/app/login")
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;
    private final OAuthService oAuthService;

    //로그인 전체
    //앱단에서 사용자 정보까지 얻어서 넘겨주면 디비 검증 후 저장하고 jwt 넘겨주기 (일반, 카카오, 애플 로그인 모두 사용)
    @ApiOperation(value = "#1일반 로그인, #3카카오 로그인", notes = "앱단에서 사용자 정보까지 얻어서 넘겨주면 디비 검증 후 저장하고 jwt 넘겨주기 (일반, 카카오, 애플 로그인 모두 사용)")
    @PostMapping("")
    public ResponseEntity<JsonResponse> appLogin(@RequestBody OAuthDto dto) {
        log.info("app Login");
        String res = oAuthService.process(dto);
        return ResponseEntity.ok(new JsonResponse(200,"login-getToken", res));
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
