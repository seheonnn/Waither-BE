package com.waither.security.oauth.service;

import com.opansoon.ops.domain.User;
import com.opansoon.ops.repository.UserRepository;
import com.opansoon.ops.security.oauth.*;
import com.waither.security.oauth.CustomAuthentication;
import com.waither.security.oauth.OAuthProcessingException;
import com.waither.security.oauth.ProviderType;
import com.waither.security.oauth.userInfo.OAuth2UserInfo;
import com.waither.security.oauth.userInfo.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return process(userRequest, oAuth2User);
    }


    // 획득한 유저정보를 Entity Model과 mapping 후 프로세스 진행
    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        ProviderType providerType = ProviderType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());

        User user = userRepository.findUserByEmail(userInfo.getEmail());

        if (user != null) { // 이미 가입한 경우
            if (providerType.toString() != user.getAuthProvider()) {
                throw new OAuthProcessingException(
                        "Please use your " + user.getAuthProvider() + " account to login."
                );
            }
//            updateUser(user, userInfo);
        } else { // 가입되지 않은 경우
            user = createUser(userInfo, providerType);
        }
        return CustomAuthentication.create(user, oAuth2User.getAttributes());
    }

    //유저 생성 - 회원가입
    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .role("USER") //RoleType 클래스 추가 시 수정
                .active(1)
                .authProvider(String.valueOf(providerType))
                .build();
        return userRepository.save(user);
    }
}
