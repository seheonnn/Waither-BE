package com.waither.security.oauth;

import java.util.Map;

public class KakaoInfo implements OAuth2UserInfo{
    private Map<String, Object> attributes;

    public KakaoInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getNickname() {
        return attributes.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getGender() {
        return attributes.get("gender").toString();
    }

    @Override
    public String getAge() {
        return attributes.get("age_range").toString();
    }
}
