package com.cy.xunwu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @SpringBootApplication中的配置exclude表示关闭http登陆验证，不过不加这个配置每次访问http都会要求输入登陆用户和密码
 */

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@RestController
public class XunwuApplication {

    public static void main(String[] args) {
        SpringApplication.run(XunwuApplication.class, args);
    }


    @RequestMapping("/hello")
    public String hello(){

        return "hello,chuyang";
    }

}
