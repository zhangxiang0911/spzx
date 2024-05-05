package com.zhangxiang.spzx.manager;

import com.zhangxiang.spzx.manager.properties.MinioProperties;
import com.zhangxiang.spzx.manager.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zhangxiang.spzx"})
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
