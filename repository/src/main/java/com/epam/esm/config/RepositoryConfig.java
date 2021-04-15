package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:default.properties")
@ComponentScan(basePackages = {"com.epam.esm"})
@EnableTransactionManagement
public class RepositoryConfig {
}