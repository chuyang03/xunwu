package com.cy.xunwu.service;

import com.cy.xunwu.entity.User;

/**
 * 用户服务
 */
public interface UserService {

    User findUserByName(String userName);
}
