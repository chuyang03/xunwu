package com.cy.xunwu.config;

import com.cy.xunwu.base.MyPasswordEncoder;
import com.cy.xunwu.security.AuthProvider;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * http权限控制
     *
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //资源访问权限
        http.authorizeRequests()
                .antMatchers("/admin/login").permitAll()    //管理员登陆入口
                .antMatchers("/static/**").permitAll()  //静态资源访问
                .antMatchers("/user/login").permitAll() //普通用户访问入口
                .antMatchers("/admin/**").hasRole("ADMIN")  //admin下的文件只有管理员能够访问
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/user/**").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .loginProcessingUrl("/login")   //配置角色登陆处理入口
                .and();

        //关闭防御策略
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

    /**
     * 自定义认证策略
     *
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //可以设置内存指定的登录的账号密码,指定角色
        //不加.passwordEncoder(new MyPasswordEncoder())
        //就不是以明文的方式进行匹配，会报错
        //auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN").and();
        auth.authenticationProvider(authProvider()).eraseCredentials(true);

        //这样，页面提交时候，密码以明文的方式进行匹配。
        //auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
//                .withUser("chuyang").password("chuyang").roles("ADMIN").and();

    }

    @Bean
    public AuthProvider authProvider(){
        return new AuthProvider();
    }

}
