package com.cora.block;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 群签名应用程序
 * @author maochaowu
 * @date 2023/4/25 19:07
 */
@MapperScan("com.cora.block.dao")
@SpringBootApplication
public class GroupSignatureApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroupSignatureApplication.class, args);
    }
}
