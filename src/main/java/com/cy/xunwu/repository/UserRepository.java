package com.cy.xunwu.repository;

import com.cy.xunwu.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 *   JPA操作的实体类,继承这个类CrudRepository，只有crud的一些简单方法
 */


public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByName(String userName);
}
