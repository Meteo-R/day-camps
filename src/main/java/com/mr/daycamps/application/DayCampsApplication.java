package com.mr.daycamps.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mr.daycamps")
public class DayCampsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayCampsApplication.class, args);
    }

}
