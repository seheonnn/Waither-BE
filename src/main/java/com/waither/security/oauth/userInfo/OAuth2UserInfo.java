package com.waither.security.oauth.userInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
}
