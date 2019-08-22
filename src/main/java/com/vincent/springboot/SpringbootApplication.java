package com.vincent.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableTransactionManagement//开启事务管理
@MapperScan("com.vincent.springboot.dao")//与dao层的@Mapper二选一写上即可(主要作用是扫包)
@SpringBootApplication
public class SpringbootApplication {
    @RequestMapping("/hh")
    public String first() {
        return "my first springboot!!";
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);
    }

}
