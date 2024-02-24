package com.huahuo.szcp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.huahuo.szcp.mapper")
public class SzcpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SzcpApplication.class, args);
    }
}
