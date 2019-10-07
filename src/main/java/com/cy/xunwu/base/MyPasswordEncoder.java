package com.cy.xunwu.base;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * 因为Spring boot 2.+ 引用的security 依赖是 spring security 5.X版本，
 * 此版本需要提供一个PasswordEncorder的实例，否则后台汇报错误：
 * java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
 *
 *
 * 解决办法：需要创建PasswordEncorder的实现类
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
