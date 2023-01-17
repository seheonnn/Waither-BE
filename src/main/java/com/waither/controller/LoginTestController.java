package com.waither.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginTestController {

    //로그인 처리 후에 사용자의 정보를 OAuth에서 얻어 반환
    @GetMapping("/login/{provider}")
    public void oauthLogin(@PathVariable String provider, HttpServletResponse response)throws IOException {
        log.info("OAuth2 Login");
        String redirect_uri = "http://localhost:8080/oauth2/authorization/" + provider;
        response.sendRedirect(redirect_uri);

    }

    //OAUth2 성공 - redirect uri로 token 반환
    @GetMapping("/token")
    public ResponseEntity<JsonResponse> token(@PathParam("token") String accessToken){
        log.info("OAuth2 Login Token Response");
        return ResponseEntity.ok(new JsonResponse(200,"login-getToken", accessToken));
    }

//    @GetMapping("/login")
//    public ResponseEntity<?> loginError(@PathParam("error")String error){
//        log.error("OAuth2 Login error");
//        throw new MethodNotAllowedException("login failure", );
//    }

}
