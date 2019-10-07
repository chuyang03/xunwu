package com.cy.xunwu.web.controller.adimin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/center")
    public String adminCenterPage(){

        return "/admin/center";
    }

    @GetMapping("/admin/welcome")
    public String welcomePage(){

        return "/admin/welcome";
    }


    //管理员登陆界面
    @GetMapping("/admin/login")
    public String adminLoginPage(){

        return "/admin/login";
    }
}
