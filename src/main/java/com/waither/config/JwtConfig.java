package com.waither.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"/application.yml"}, factory = YamlPropertySourceFactory.class)
public class JwtConfig {

    @Value("${jwt.secret}")
    public String SECRET_KEY;

}
