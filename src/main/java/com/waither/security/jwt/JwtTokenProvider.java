package com.waither.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;

@Log4j2
@Component
public class JwtTokenProvider {
    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60; //1H
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 14; //2WEEK
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); //yml에 설정

    //accessToken 생성
//    public String createAccessToken(Authentication authentication, Long expiration) {
//
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH); //token 만료 시간 설정


//        assert authentication != null;

//        OAuth2UserInfoIf oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo((OAuth2AuthenticationToken) authentication);
//        String provider = oAuth2UserInfo.getProvider().toString();
//        String uid = oAuth2UserInfo.getId();
//        String email = oAuth2UserInfo.getEmail();

//        List<String> authorities = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//
//        return Jwts.builder()
//                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
//                .setSubject(uid)
//                .claim(PROVIDER, provider)
//                .claim(EMAIL, email)
//                .claim(AUTHORITY, authorities)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SECRET_KEY)
//                .compact();
//    }


    public String createToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    //refreshToken 생성
    public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

//        String refreshToken = Jwts.builder()
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                .setIssuer("debrains")
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .compact();

    }


    //refreshToken 저장
    private void saveRefreshToken(Authentication authentication, String refreshToken) {
    }

    //accessToken 검사 후 Authentication 객체 생성
//    public Authentication getAuthentication(String accessToken) {
//        Claims claims = parseClaims(accessToken);
//
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        User principal = new User(claims.getSubject(), "", authorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }

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

    // accessToken 만료 시 갱신 때 사용할 정보를 얻기 위해 Claim return
//    private Claims parseClaims(String accessToken) {
//        try {
//            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims();
//        }
//    }
}