package com.lt.crs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.lt.configuration.SpringJDBCConfiguration;

@ComponentScan({"com.lt.*"})
@Configuration
@EnableWebMvc
@EnableAutoConfiguration
@SpringBootApplication
@Import({SpringJDBCConfiguration.class})
public class CrsLtGrp3SpringRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsLtGrp3SpringRestApplication.class, args);
	}

}
