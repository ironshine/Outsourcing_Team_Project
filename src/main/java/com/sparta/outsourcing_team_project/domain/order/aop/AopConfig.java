package com.sparta.outsourcing_team_project.domain.order.aop;

import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    private final UserRepository userRepository;

    @Autowired
    public AopConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public LoggingAspect getAspect() {
        return new LoggingAspect(userRepository);
    }
}