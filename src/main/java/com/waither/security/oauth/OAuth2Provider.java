package com.waither.security.oauth;

import java.util.Arrays;
import java.util.Optional;

public enum OAuth2Provider {
    KAKAO,
    APPLE;

    public static Optional<OAuth2Provider> convert(String registrationId) {
        return Arrays.stream(OAuth2Provider.values())
                .filter(oAuth2Provider -> oAuth2Provider.toString().equals(registrationId.toUpperCase()))
                .findAny();
    }
}
