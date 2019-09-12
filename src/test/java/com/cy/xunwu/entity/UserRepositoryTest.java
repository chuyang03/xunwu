package com.cy.xunwu.entity;

import com.cy.xunwu.XunwuApplicationTests;
import com.cy.xunwu.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public class UserRepositoryTest extends XunwuApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOne(){

        Optional<User> optionalUser = userRepository.findById(1L);
        User user = optionalUser.get();

        //判断数据库中名字和用JPA得到用户的名字是否相等
        Assert.assertEquals("wali", user.getName());
    }
}
