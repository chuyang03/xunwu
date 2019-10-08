package com.cy.xunwu.web.controller.adimin;


import com.cy.xunwu.base.ApiResponse;
import com.cy.xunwu.service.house.QiNiuService;
import com.cy.xunwu.web.dto.QiNiuPutResult;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AdminController {

    @Autowired
    private QiNiuService qiNiuService;

    @Autowired
    private Gson gson;

    @GetMapping("/admin/center")
    public String adminCenterPage(){

        return "admin/center";
    }

    @GetMapping("/admin/welcome")
    public String welcomePage(){

        return "admin/welcome";
    }


    //管理员登陆界面
    @GetMapping("/admin/login")
    public String adminLoginPage(){

        return "admin/login";
    }

    //添加房源
    @GetMapping("/admin/add/house")
    public String addHousePage(){

        return "admin/house-add";
    }


    //上传图片到本地
    @PostMapping(value = "admin/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()){

            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }

        String fileName = file.getOriginalFilename();

        try {
            InputStream inputStream = file.getInputStream();
            //这里的response是七牛云自带的
            Response response = qiNiuService.uploadFile(inputStream);
            //如果上传成功
            if (response.isOK()){

                //把一个json字符串转换成了一个结果集对象
                QiNiuPutResult qiNiuPutResult = gson.fromJson(response.bodyString(), QiNiuPutResult.class);

                //将上传图片后的结果集对象返回给前端
                return ApiResponse.ofSuccess(qiNiuPutResult);
            }else {

                return ApiResponse.ofMessage(response.statusCode, response.getInfo());
            }

        } catch (QiniuException e){

            Response response = e.response;

            try {
                return ApiResponse.ofMessage(response.statusCode, response.bodyString());
            } catch (QiniuException ex) {
                ex.printStackTrace();
                return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {

            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     *
     * //上传图片到本地
     *     @PostMapping(value = "admin/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     *     @ResponseBody
     *     public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file){
     *
     *         if (file.isEmpty()){
     *
     *             return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
     *         }
     *
     *         String fileName = file.getOriginalFilename();
     *         //将要上传图片的文件夹
     *         File target = new File("/Users/chuyangyang/IdeaProjects/xunwu/tmp/"+fileName);
     *
     *         //上传图片
     *         try {
     *             file.transferTo(target);
     *         } catch (IOException e) {
     *             e.printStackTrace();
     *
     *             return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
     *         }
     *
     *         return ApiResponse.ofSuccess(null);
     *     }
     *
     */

}
