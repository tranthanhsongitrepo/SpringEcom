package com.example.springecom.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
    @Value("${sql.username}")
    private String username;

    @Value("${sql.password}")
    private String password;

    @Value("${sql.url}")
    String url;

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().password(password).username(username).url(url).build();
    }
}
