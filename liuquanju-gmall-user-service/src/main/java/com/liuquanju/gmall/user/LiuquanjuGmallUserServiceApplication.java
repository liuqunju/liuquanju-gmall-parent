package com.liuquanju.gmall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.liuquanju.gmall.user.mapper")
public class LiuquanjuGmallUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiuquanjuGmallUserServiceApplication.class, args);
    }

}
