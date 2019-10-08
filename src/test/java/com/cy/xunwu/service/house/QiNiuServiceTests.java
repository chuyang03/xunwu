package com.cy.xunwu.service.house;

import com.cy.xunwu.XunwuApplicationTests;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class QiNiuServiceTests extends XunwuApplicationTests {

    @Autowired
    private QiNiuService qiNiuService;

    //测试上传图片
    @Test
    public void testUploadFile(){

        String fileName = "/Users/chuyangyang/IdeaProjects/xunwu/tmp/di.jpg";
        File file = new File(fileName);

        //断言，这个文件是否存在
        Assert.assertTrue(file.exists());

        try {
            Response response = qiNiuService.uploadFile(file);
            Assert.assertTrue(response.isOK());

        } catch (QiniuException e) {
            e.printStackTrace();
        }


    }

    //删除七牛云文件
    @Test
    public void testDelete(){

        String key = "FtXE5hvTalTZJovlgTCtVFLh05Mq";

        try {
            Response response = qiNiuService.delete(key);

            Assert.assertTrue(response.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }


    }
}
