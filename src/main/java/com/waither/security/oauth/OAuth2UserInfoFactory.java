package com.waither.security.oauth;

import java.util.Map;
import java.util.Optional;

import static com.waither.security.oauth.OAuth2Provider.KAKAO;


public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOauth2UserInfo(String authProviderId, Map<String, Object> attributes) {
        OAuth2UserInfo userInfo = null;
        Optional<OAuth2Provider> oAuth2Provider = OAuth2Provider.convert(authProviderId);
        if (oAuth2Provider.equals(KAKAO)) {
            return new KakaoInfo(attributes);
        } //추후 다른 소셜로그인 추가 가능
        throw new IllegalArgumentException("Invalid Provider Type");
    }
}
