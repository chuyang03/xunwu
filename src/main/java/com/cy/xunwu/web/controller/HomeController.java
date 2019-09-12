package com.cy.xunwu.web.controller;

import com.cy.xunwu.base.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("name", "储阳阳");

        //默认去找templates文件夹下面的index.html
        return "index";
    }

    @GetMapping("/get")
    @ResponseBody
    public ApiResponse get(){

        return ApiResponse.ofMessage(200, "成功了");
    }

}