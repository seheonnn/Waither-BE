package com.waither.service;

import com.waither.config.JwtConfig;
import com.waither.config.YamlPropertySourceFactory;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import static com.waither.security.jwt.JwtTokenProvider.SECRET_KEY;

@Log4j2
@Service
@PropertySource(value = {"/application.yml"}, factory = YamlPropertySourceFactory.class)
@RequiredArgsConstructor
public class JwtService {

    public static Long getUserIdx (String token) {
        return Long.valueOf(Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject());
    }
}
