package com.mr.daycamps.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.mr.daycamps")
@EnableJpaRepositories(basePackages = "com.mr.daycamps.infrastructure")
@EntityScan("com.mr.daycamps.infrastructure")
public class DayCampsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayCampsApplication.class, args);
    }

}
