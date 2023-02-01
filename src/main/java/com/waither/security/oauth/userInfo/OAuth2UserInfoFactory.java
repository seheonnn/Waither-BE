package com.waither.security.oauth.userInfo;

import com.waither.security.oauth.ProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        return switch (providerType) {
            case KAKAO -> new KakaoOAuth2Info(attributes);
            case APPLE -> new AppleOAuth2Info(attributes);
            default -> throw new IllegalArgumentException("Invalid Provider Type");
        };
    }
}
