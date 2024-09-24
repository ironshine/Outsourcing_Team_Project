package com.sparta.outsourcing_team_project.domain.order.aop;

import com.sparta.outsourcing_team_project.domain.order.dto.OrderAopDto;
import com.sparta.outsourcing_team_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_team_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class AopConfig {

    @Bean
    public OrderAopDto orderAopDto() {
        return new OrderAopDto();
    }
}