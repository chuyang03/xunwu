package com.cy.xunwu.web.controller.adimin;


import com.cy.xunwu.base.ApiResponse;
import com.cy.xunwu.entity.SupportAddress;
import com.cy.xunwu.service.house.AddressService;
import com.cy.xunwu.service.house.HouseService;
import com.cy.xunwu.service.house.QiNiuService;
import com.cy.xunwu.web.dto.HouseDTO;
import com.cy.xunwu.web.dto.QiNiuPutResult;
import com.cy.xunwu.web.dto.ServiceResult;
import com.cy.xunwu.web.dto.SupportAddressDTO;
import com.cy.xunwu.web.form.HouseForm;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private QiNiuService qiNiuService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private HouseService houseService;

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


    /**
     * @ModelAttribute("form-house-add")这个注解表示将这个表单与前端的表单进行绑定
     *
     */
    //添加房源信息
    @PostMapping("admin/add/house")
    @ResponseBody
    public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add") HouseForm houseForm,
                                BindingResult bindingResult){

        //如果绑定结果出错
        if (bindingResult.hasErrors()){
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }

        if (houseForm.getPhotos() == null || houseForm.getCover() == null){
            return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), "必须上传图片");
        }

        Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
        if (addressMap.keySet().size() != 2){

            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }

        /**
         * 自定义结果集ServiceResult包含三个属性，成功的标志，失败需要返回的信息，返回的数据
         *
         * success、message、result
         */
        ServiceResult<HouseDTO> serviceResult = houseService.save(houseForm);
        if (serviceResult.isSuccess()){

            return ApiResponse.ofSuccess(serviceResult.getResult());
        }

        return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
    }

}
