package com.easy.work;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan(basePackages = {"com.easy.api.service","com.easy.service.impl"})
@MapperScan("com.easy.work.dao")
@SpringBootApplication
public class WorkWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkWebApplication.class, args);
    }

}

