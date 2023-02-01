package com.waither;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class WaitherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaitherApplication.class, args);
	}
}