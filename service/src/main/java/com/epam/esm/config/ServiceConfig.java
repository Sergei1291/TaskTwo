package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(RepositoryConfig.class)
@ComponentScan(basePackages = {"com.epam.esm"})
@EnableTransactionManagement
public class ServiceConfig {
}