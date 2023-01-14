package com.waither.security.oauth.userInfo;

import com.waither.security.oauth.ProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case KAKAO: return new KakaoOAuth2Info(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type");
        }
    }
}
