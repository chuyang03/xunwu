package com.cy.xunwu.security;

import com.cy.xunwu.entity.User;
import com.cy.xunwu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.DigestUtils;

/**
 *
 * 自定义认证实现
 */
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();


        User user = userService.findUserByName(userName);

        if (user == null){
            throw new AuthenticationCredentialsNotFoundException("authError");
        }


        String stringId = inputPassword + String.valueOf(user.getId());
        String md5Pass = DigestUtils.md5DigestAsHex(stringId.getBytes());

        if (user.getPassword().equals(md5Pass)){

            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }else {
            throw new BadCredentialsException("authError");
        }


//        if (this.passwordEncoder.isPasswordValid(user.getPassword(), inputPassword, user.getId())) {
//            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        }else {
//            throw new BadCredentialsException("authError");
//        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //支持所有的认证类
        return true;
    }
}
