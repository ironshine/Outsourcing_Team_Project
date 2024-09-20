package com.sparta.outsourcing_team_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class OutsourcingTeamProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutsourcingTeamProjectApplication.class, args);
    }

}
