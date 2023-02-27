package com.waither.security.oauth.service;

import com.waither.entities.UserEntity;
import com.waither.repository.UserRepository;
import com.waither.security.oauth.CustomAuthentication;
import com.waither.security.oauth.OAuthProcessingException;
import com.waither.security.oauth.ProviderType;
import com.waither.security.oauth.RoleType;
import com.waither.security.oauth.userInfo.OAuth2UserInfo;
import com.waither.security.oauth.userInfo.OAuth2UserInfoFactory;
//import com.waither.security.test.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("OAuth2 Login - load user");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User : " + oAuth2User.toString());

        try {
            return process(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }


    // 획득한 유저정보를 Entity Model과 mapping 후 프로세스 진행
    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        log.info("OAuth2 Login - process");
        ProviderType providerType = ProviderType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        log.info("OAuth2 Login - process : " + providerType.toString());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());
//        log.info("OAuth2 Login - process : " + userInfo.getEmail());

        UserEntity user = userRepository.findUserByAuthId(userInfo.getId());

        if (user != null) { // 이미 가입한 경우
            log.info("OAuth2 - process : " + providerType.toString());
            if (!providerType.toString().equals(user.getProvider())) {
                throw new OAuthProcessingException(
                        "Please use your " + user.getProvider() + " account to login."
                );
            }
//            updateUser(user, userInfo);
        } else { // 가입되지 않은 경우
            user = createUser(userInfo, providerType);
        }
        return CustomAuthentication.create(user, oAuth2User.getAttributes());
    }

    //유저 생성 - 회원가입
    private UserEntity createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        log.info("OAuth2 Login - createUser : " + userInfo.getId());
        UserEntity user = UserEntity.builder()
                .userName(userInfo.getName())
                .authId(userInfo.getId())
                .email(encoder.encode(userInfo.getEmail()))
//                .email(userInfo.getEmail())
                .pw("")
                .role(String.valueOf(RoleType.USER)) //RoleType 클래스 추가함
                .status('A')
                .provider(String.valueOf(providerType))
                .build();
        return userRepository.save(user);
    }
}
