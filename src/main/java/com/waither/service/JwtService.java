package com.waither.service;

import io.jsonwebtoken.Jwts;

public class JwtService {

//    @Value("jwt.secret")
    private static final String SECRET_KEY = "6B64DCA4E72F58EDIKU9AAB215FE7"; //yml에 설정


    public static Long getuserIdx(String token) {
        return Long.valueOf(Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject())
                ;
    }
}
