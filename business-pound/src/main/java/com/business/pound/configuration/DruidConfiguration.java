package com.business.pound.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfiguration {
    @Bean(name = "vipmemberDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.vipmember")
    public DruidDataSource dataSource(DataSourceProperties properties) {
        return  DruidDataSourceBuilder.create().build();
    }
}
