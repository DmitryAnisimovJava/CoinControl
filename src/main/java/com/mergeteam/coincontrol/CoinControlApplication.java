package com.mergeteam.coincontrol;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayConnectionDetails;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaRepositories(basePackages = "com.mergeteam.coincontrol.repository")
public class CoinControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoinControlApplication.class, args);
    }


    @Bean
    @Primary
    public FlywayConnectionDetails getConnectionDetails(FlywayProperties flywayProperties) {
        return new FlywayConnectionDetails() {
            @Override
            public String getUsername() {
                return flywayProperties.getUser();
            }

            @Override
            public String getPassword() {
                return flywayProperties.getPassword();
            }

            @Override
            public String getJdbcUrl() {
                return flywayProperties.getUrl();
            }
        };
    }

    @Bean
    @Primary
    public JdbcConnectionDetails getJdbcConnectionDetails(DataSourceProperties dataSourceProperties) {
        return new JdbcConnectionDetails() {
            @Override
            public String getUsername() {
                return dataSourceProperties.getUsername();
            }

            @Override
            public String getPassword() {
                return dataSourceProperties.getPassword();
            }

            @Override
            public String getJdbcUrl() {
                return dataSourceProperties.getUrl();
            }

            @Override
            public String getXaDataSourceClassName() {
                return (dataSourceProperties.getXa().getDataSourceClassName() != null)
                        ? dataSourceProperties.getXa().getDataSourceClassName()
                        : JdbcConnectionDetails.super.getXaDataSourceClassName();
            }
        };
    }
}
