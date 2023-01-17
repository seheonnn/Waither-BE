package com.waither.config;

import com.waither.security.jwt.JwtAuthenticationEntryPoint;
import com.waither.security.jwt.JwtAuthenticationFilter;
import com.waither.security.jwt.JwtTokenProvider;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    private final JwtTokenProvider jwtTokenProvider;

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().antMatchers("/**"); //ignoring을 하면 filter 자체를 타지 않음
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests() //보호된 uri에 접근할 수 있는 권한 설정
                .antMatchers("/login/**").permitAll()
                .antMatchers("/token/**").permitAll()
                .anyRequest().permitAll() //해당 url 요청 전체 허용
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(cookieAuthorizationRepository)
                .and()
                .redirectionEndpoint()
//                .baseUri("/oauth2/callback/*") // 소셜 인증 후 redirect uri
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService) // 소셜에서 회원 정보 받아와 가공
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler);

        httpSecurity.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) //JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 이전에 삽입
                .logout().permitAll();

        return httpSecurity.build();
    }
}
