package com.waither.controller;

import com.waither.domain.JsonResponse;
import com.waither.dto.OAuthDto;
import com.waither.security.oauth.service.OAuthService;
import com.waither.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/app/login")
public class LoginController {

    @Autowired
    UserService userService;
    private final OAuthService oAuthService;

    //1 일반 로그인 3 카카오 로그인
    //앱단에서 사용자 정보까지 얻어서 넘겨주면 디비 검증 후 저장하고 jwt 넘겨주기 (일반, 카카오, 애플 로그인 모두 사용)
    @PostMapping("/app/login")
    public ResponseEntity<JsonResponse> appLogin(@RequestBody OAuthDto dto) {
        log.info("app Login");
        String res = oAuthService.process(dto);
        return ResponseEntity.ok(new JsonResponse(200,"login-getToken", res));
    }


}
