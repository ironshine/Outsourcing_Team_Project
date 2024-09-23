package com.sparta.outsourcing_team_project.domain.order.aop;

import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class AopConfig {

    private UserRepository userRepository;

    @Bean
    public LoggingAspect getAspect(){
        return new LoggingAspect();
    }
}
