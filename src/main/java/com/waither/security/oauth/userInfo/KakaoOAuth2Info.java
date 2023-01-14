package com.waither.security.oauth.userInfo;

import java.util.Map;

public class KakaoOAuth2Info extends OAuth2UserInfo{
    public KakaoOAuth2Info(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("account_email");
    }

    @Override
    public String getGender() {
        return attributes.get("gender").toString();
    }

    @Override
    public int getAge() {
        return (int) attributes.get("age_range");
    }

}
