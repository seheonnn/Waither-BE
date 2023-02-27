package com.waither.security.oauth.service;

import com.waither.dto.OAuthDto;
import com.waither.entities.UserEntity;
import com.waither.repository.UserRepository;
import com.waither.security.jwt.JwtTokenProvider;
import com.waither.security.oauth.CustomAuthentication;
import com.waither.security.oauth.OAuthProcessingException;
import com.waither.security.oauth.ProviderType;
import com.waither.security.oauth.RoleType;
import com.waither.security.oauth.userInfo.OAuth2UserInfo;
import com.waither.security.oauth.userInfo.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String process(OAuthDto oAuthDto) {
        log.info("OAuth Login App - process");
        ProviderType providerType = ProviderType.valueOf(oAuthDto.getProvider().toUpperCase());
        log.info("OAuth Login App - process : " + providerType);
//        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());
//        log.info("OAuth2 Login - process : " + userInfo.getEmail());

        UserEntity user = userRepository.findUserByAuthId(oAuthDto.getAuthId());

        if (user != null) { // 이미 가입한 경우
            if (!providerType.toString().equals(user.getProvider())) {
                throw new OAuthProcessingException(
                        "Please use your " + user.getProvider() + " account to login."
                );
            }
//            updateUser(user, userInfo);
        } else { // 가입되지 않은 경우
            user = createUser(oAuthDto, providerType);
        }
        return tokenProvider.createJwt(user);
    }


    //유저 생성 - 회원가입
    private UserEntity createUser(OAuthDto userInfo, ProviderType providerType) {
        log.info("OAuth Login App - createUser : " + userInfo.getAuthId());
        UserEntity user = UserEntity.builder()
                .userName(userInfo.getNickname())
                .authId(userInfo.getAuthId())
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
