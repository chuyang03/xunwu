package com.cy.xunwu.service.user;

import com.cy.xunwu.entity.Role;
import com.cy.xunwu.entity.User;
import com.cy.xunwu.repository.RoleRepository;
import com.cy.xunwu.repository.UserRepository;
import com.cy.xunwu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findUserByName(String userName) {

        User user = userRepository.findUserByName(userName);

        if (user == null){
            return null;
        }

        //查询用户对应的角色
        List<Role> roles = roleRepository.findRolesByUserId(user.getId());

        if (roles == null || roles.isEmpty()){
            throw new DisabledException("权限非法");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        //lambda表达式，从用户拥有的角色集合中遍历角色，添加到该用户所拥有的权限集合中
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));

        user.setAuthorityList(authorities);

        return user;
    }
}
