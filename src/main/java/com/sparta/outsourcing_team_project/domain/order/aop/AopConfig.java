package com.sparta.outsourcing_team_project.domain.order.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class AopConfig {

    @Bean
    public LoggingAspect getAspect(){return new LoggingAspect();}
}