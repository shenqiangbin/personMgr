package com.sqber.personMgr.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;

import javax.sql.DataSource;

@Configuration
public class DataSourceMySqlConfig {

    @Bean(name = "MySQLDS")
    @ConfigurationProperties(prefix = "spring.datasource.mysql") // application.properteis中对应属性的前缀
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }
}
