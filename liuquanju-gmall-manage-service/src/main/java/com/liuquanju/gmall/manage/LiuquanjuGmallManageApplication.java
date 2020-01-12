package com.liuquanju.gmall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.liuquanju.gmall.manage.mapper")
public class LiuquanjuGmallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiuquanjuGmallManageApplication.class, args);
    }

}
