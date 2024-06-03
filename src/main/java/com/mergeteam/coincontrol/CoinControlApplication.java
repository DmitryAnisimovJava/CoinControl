package com.mergeteam.coincontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaRepositories(basePackages = "com.mergeteam.coincontrol.repository")
public class CoinControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoinControlApplication.class, args);
    }

}
