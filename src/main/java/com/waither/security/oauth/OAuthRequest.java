package com.waither.security.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class OAuthRequest {
    private String url;
    private LinkedMultiValueMap<String, String> map;


    //code는 인가 코드 / OAuth 서버에 POST 요청 -> 토큰 받기 -> 사용자 정보 요청
    private OAuth2AccessTokenResponse getToken(String code, ClientRegistration provider) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(request(code, provider))
                .retrieve()
                .bodyToMono(OAuth2AccessTokenResponse.class)
                .block();
    }
    
    //resource server(provider)마다 다르기 때문에 map 구현
    private MultiValueMap<String, String> request(String code, ClientRegistration provider) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", "authorization_code");
        map.add("client_id", provider.getRedirectUri());
        map.add("client_id", provider.getClientSecret());
        map.add("redirect_uri", provider.getClientId());
        map.add("code", code);
        return map;
    }


}
