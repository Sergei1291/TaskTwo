package com.epam.esm.config;

import com.epam.esm.mapper.GiftCertificateDtoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
public class ServiceConfig {

    @Bean
    public GiftCertificateDtoMapper giftCertificateDtoMapper() {
        return Mappers.getMapper(GiftCertificateDtoMapper.class);
    }

}