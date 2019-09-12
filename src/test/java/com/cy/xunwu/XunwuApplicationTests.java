package com.cy.xunwu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


/**
 *
 * @ActiveProfiles("test")表示测试的时候激活application-test.properties
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@Configuration
//@ActiveProfiles("test")
public class XunwuApplicationTests {


    //这个是一个主测试类，创建其他测试类的时候只需要继承这个类就可以了

}
