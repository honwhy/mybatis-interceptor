package com.github.honwhy.mybatisinterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.github.honwhy.mybatisinterceptor"})
@SpringBootApplication
public class MybatisInterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisInterceptorApplication.class, args);
    }

}
