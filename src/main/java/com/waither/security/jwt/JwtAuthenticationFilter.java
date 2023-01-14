package com.waither.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { //GenericFilterBean - 새필터로직 수행, OncePerRequestFilter - 동일 클라이언트는 동일 서블릿 서빙

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //Request Jwt 유효성 검증 filter를 filterChain에 등록
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String token = resolveToken(httpServletRequest); //header에서 accessToken 추출
//        String requestUri = httpServletRequest.getRequestURI();
//
//        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) { //accessToken 유효성 validate
//            Authentication authentication = jwtTokenProvider(token);
////            SecurityContextHolder.getContext().setAuthentication(authentication);
////            logger.debug("Security Context에 '{}' 인증 정보 저장, uri: {}", authentication.getName(), requestUri);
//        } else {
//            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestUri);
//        }
//
//        chain.doFilter(request, response);
//    }

    //Request Jwt 유효성 검증 filter를 filterChain에 등록
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = resolveToken(httpServletRequest); //header에서 accessToken 추출
        String requestUri = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) { //accessToken 유효성 validate
//            Authentication authentication = jwtTokenProvider(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            logger.debug("Security Context에 '{}' 인증 정보 저장, uri: {}", authentication.getName(), requestUri);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestUri);
        }

        filterChain.doFilter(request, response);
    }

    //request header 토큰 정보 get
    private String resolveToken(HttpServletRequest request) {
        String BearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(BearerToken) && BearerToken.startsWith("Bearer ")) {
            return BearerToken.substring(7);
        }
        return null;
    }
}