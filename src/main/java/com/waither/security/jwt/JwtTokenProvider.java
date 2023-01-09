package com.waither.security.jwt;

import com.waither.security.oauth.OAuth2UserInfo;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtTokenProvider {
    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60; //1H
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 14; //2WEEK

    private final String SECRET_KEY = "WAITHER"; //나중에 숨겨야함

    //accessToken 생성
    public String createAccessToken(Authentication authentication, Long expiration) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH); //token 만료 시간 설정

        assert authentication != null;

//        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo((OAuth2AuthenticationToken) authentication);
//        String provider = oAuth2UserInfo.getProvider().toString();
//        String email = oAuth2UserInfo.getEmail();
//        String name = oAuth2UserInfo.getName();

//        List<String> authorities = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 설
                .setSubject("user") //토큰 용도 설정
                .setClaims(OAuth2UserInfo.getPayload()) //payload 설정
//                .claim(EMAIL, email)
//                .claim(NAME, name)
//                .claim(AUTHORITY, authorities) //권한설정 -> 필요없음
                .setIssuedAt(now)
                .setExpiration(expiryDate) //기한 설정
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes()) //나중에 숨겨야함
                .compact();
    }

    //refreshToken 생성
    public String createRefreshToken(Authentication authentication, HttpServletResponse response) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes()) //나중에 숨겨야함
                .setIssuer("waither")
                .setIssuedAt(now) //
                .setExpiration(validity) //기한설정
                .compact();
    }

    //refreshToken 저장
    private void saveRefreshToken(Authentication authentication, String refreshToken) {
    }

    //accessToken 검사 후 Authentication 객체 생성
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    //token 유효성 검증
    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)) // Key Setting
                    .parseClaimsJws(token); //파싱 및 검증
            return true;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalStateException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }

    // accessToken 만료 시 갱신 때 사용할 정보를 얻기 위해 Claim return
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
