package com.cy.xunwu.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * web错误  全局配置
 */
@Controller
public class AppErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @Autowired
    public AppErrorController(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }

    /**
     * web页面错误处理
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorPageHandler(HttpServletRequest request, HttpServletResponse response){

        int status = response.getStatus();

        switch (status){

            case 403:
                return "403";
            case 404:
                return "404";
            case 500:
                return "500";
        }

        return "index";
    }

    /**
     * 除web页面外的错误处理，比如Json/XML等
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ApiResponse errorApihandler(HttpServletRequest request){

        //ServletWebRequest构造器中调用的是父类ServletRequestAttributes构造方法，
        // 所以和使用ServletRequestAttributes包装request是一样的
        //
        // ServletRequestAttributes(javax.servlet.http.HttpServletRequest)
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);

        //getErrorAttributes这个方法中的第一个参数类型是WebRequest的,ServletWebRequest是WebRequest的实现类
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(servletWebRequest, false);

        int status = getStatus(request);

        return ApiResponse.ofMessage(status, String.valueOf(attr.getOrDefault("message", "error")));
    }

    private int getStatus(HttpServletRequest request){

        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (status != null){
            return status;
        }

        return 500;
    }
}
