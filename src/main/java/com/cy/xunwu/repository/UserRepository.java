package com.cy.xunwu.repository;

import com.cy.xunwu.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 *   JPA操作的实体类
 */


public interface UserRepository extends CrudRepository<User, Long> {
}
