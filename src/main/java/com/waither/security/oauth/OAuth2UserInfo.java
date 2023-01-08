package com.waither.security.oauth;

import java.util.Map;

public interface OAuth2UserInfo {
    Map<String, Object> getAttributes();
    String getProviderId();
    String getProvider();
    String getNickname();
    String getEmail();
    String getGender();
    String getAge();
}
