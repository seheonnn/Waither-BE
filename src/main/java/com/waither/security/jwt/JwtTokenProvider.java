package com.waither.security.jwt;

import com.waither.security.oauth.CustomAuthentication;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtTokenProvider {

    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60; //1H
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 14; //2WEEK
    private static final String SECRET_KEY = "6B64DCA4E72F58EDIKU9AAB215FE7"; //yml에 설정


    //accessToken 생성
    public String createAccessToken(Authentication authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

        log.info("create access token - tokenProvider " + authentication.getPrincipal().toString());
        log.info("create access token - tokenProvider " + authentication.getName());

        CustomAuthentication user = (CustomAuthentication) authentication.getPrincipal();

        String userId = user.getId(); //social id(=authId)
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(now) //token 발행 시간
                .setExpiration(validity)
                .compact();
    }


    //refreshToken 생성
    public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

        String refreshToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    //token 유효성 검증
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (NullPointerException ex){
            log.error("JWT is empty");
        } catch (IllegalStateException e) {
            log.info("JWT is illegal");
        }
        return false;
    }

    // jwt access Token을 검사하고 얻은 정보로 Authentication 객체 생성
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
//
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        CustomAuthentication authentication = new CustomAuthentication(claims.getSubject(), null, authorities);

        return new UsernamePasswordAuthenticationToken(authentication, "", authorities);
    }

    // Access Token 만료시 갱신때 사용할 정보를 얻기 위해 Claim 리턴 : id, email
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}