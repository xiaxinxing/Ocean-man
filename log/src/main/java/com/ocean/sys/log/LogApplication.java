package com.ocean.sys.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 本服务主要是做一些日志记录功能及 利用swaggerui生成api接口文档
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.ocean.sys.log")
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }

}
