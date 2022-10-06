package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@SpringBootApplication
@MapperScan("com.example.mapper")
public class Java1234MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(Java1234MallApplication.class, args);
    }

}
