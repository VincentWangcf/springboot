package com.vincent.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableTransactionManagement//开启事务管理
@MapperScan("com.vincent.springboot.*")//与dao层的@Mapper二选一写上即可(主要作用是扫包)
@SpringBootApplication
public class SpringbootApplication {
    @RequestMapping("/hh")
    public String first() {
        new Thread(() -> {
            // 4. 启动客户授信审批流程
            // 流程节点顺序：1、准入 2、额度审批 3、征信及反欺诈过滤
            // 合同表id作为交易表id，入参给taskId
            try {
                System.out.println("hhhhh");
            } catch (Exception e) {

            }
        }).start();
        return "my first springboot!!";
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);
    }

}
