package com.waither.security.oauth;

import java.util.HashMap;
import java.util.Map;

public class OAuth2UserInfo {
    static Map<String, Object> payloads = new HashMap<>();

    public static Map<String, Object> getPayload(){
        payloads.put("userIdx",getIdx());
        payloads.put("userName",getName());
        payloads.put("id",getId());
        return payloads;
    }
//    String getProviderId();
//String getNickname();
//String getGender();
//String getAge();
//    String getProvider();
static String getId(){
        return "abc123@gamil.com";
    };

    static int getIdx(){
        return 2;
    };

    static String getName(){
        return "동키";
    }
}
