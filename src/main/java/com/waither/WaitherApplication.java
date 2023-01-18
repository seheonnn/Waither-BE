package com.waither;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

//Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.를 위해 db사용X 추가
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class WaitherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaitherApplication.class, args);
	}
}