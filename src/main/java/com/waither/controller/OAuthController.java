package com.waither.controller;

import com.waither.domain.JsonResponse;
import com.waither.dto.OAuthDto;
import com.waither.security.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
    private final OAuthService oAuthService;

    //로그인 처리 후에 사용자의 정보를 OAuth에서 얻어 반환
    @GetMapping("/login/{provider}")
    public void oauthLogin(@PathVariable String provider, HttpServletResponse response)throws IOException {
        log.info("OAuth2 Login");
        String redirect_uri = "http://localhost:8080/oauth2/authorization/" + provider; // <- 이거 주석하고
//        String redirect_uri = "https://waither.shop/oauth2/authorization/" + provider; // <- 이거 주석 풀기
        response.sendRedirect(redirect_uri);

    }

    //OAuth2 성공 - redirect uri로 token 반환
    @GetMapping("/token")
    public ResponseEntity<JsonResponse> token(@PathParam("token") String accessToken){
        log.info("OAuth2 Login Token Response");
        return ResponseEntity.ok(new JsonResponse(200,"login-getToken", accessToken));
    }

    //앱단에서 사용자 정보까지 얻어서 넘겨주면 디비 검증 후 저장하고 jwt 넘겨주기 (일반, 카카오, 애플 로그인 모두 사용)
    @PostMapping("/app/login")
    public ResponseEntity<JsonResponse> appLogin(@RequestBody OAuthDto dto) {
        log.info("app Login");
        String res = oAuthService.process(dto);
        return ResponseEntity.ok(new JsonResponse(200,"login-getToken", res));

    }

}
