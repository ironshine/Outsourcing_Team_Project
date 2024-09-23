package com.sparta.outsourcing_team_project.domain.order.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public LoggingAspect getAspect(){
        return new LoggingAspect();
    }
}
