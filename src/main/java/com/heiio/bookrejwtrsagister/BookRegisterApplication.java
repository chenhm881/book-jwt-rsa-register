package com.heiio.bookrejwtrsagister;

import com.heiio.bookrejwtrsagister.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BookRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookRegisterApplication.class, args);
    }

}
