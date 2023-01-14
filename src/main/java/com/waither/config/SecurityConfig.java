package com.waither.config;

import com.waither.security.oauth.cookie.CookieAuthorizationRepository;
import com.waither.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.waither.security.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.waither.security.oauth.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final CookieAuthorizationRepository cookieAuthorizationRepository;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/**"); //ignoring을 하면 filter 자체를 타지 않음
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") //관리자 페이지
                .antMatchers("/wish").hasRole("USER") //로그인 유저만 찜 관련 허용
                .anyRequest().permitAll() //해당 url 요청 토큰 없어도 허용
                .and()
                .anonymous().principal("anonymousUser").authorities("ROLE_ANONYMOUS");
        httpSecurity.formLogin().disable()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRepository)
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler);
        return httpSecurity.build();
    }
}
