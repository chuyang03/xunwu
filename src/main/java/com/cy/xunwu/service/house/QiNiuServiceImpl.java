package com.cy.xunwu.service.house;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
public class QiNiuServiceImpl implements QiNiuService, InitializingBean {

    //这是上传文件管理器，使用这个实例上传文件
    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.Bucket}")
    private String bucket;

    private StringMap putPolicy;

    @Override
    public Response uploadFile(File file) throws QiniuException {

        Response response = this.uploadManager.put(file, null, getUploadToken());

        //如果上传失败，重新上传次数
        int retry = 0;
        while (response.needRetry() && retry < 3){

            response = this.uploadManager.put(file, null, getUploadToken());
            retry++;
        }
        return response;
    }

    //使用文件流的方式上传文件，大多数情况下，将file变成流来调用这个函数
    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {

        Response response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);

        //如果上传失败，重新上传次数
        int retry = 0;
        while (response.needRetry() && retry < 3){

            response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
            retry++;
        }
        return response;
    }

    //从七牛云上面删除文件
    @Override
    public Response delete(String key) throws QiniuException {

        Response response = bucketManager.delete(this.bucket, key);
        //如果上传失败，重新上传次数
        int retry = 0;
        while (response.needRetry() && retry < 3){

            response = bucketManager.delete(this.bucket, key);
            retry++;
        }
        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();

        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width),\"height\":$(imageInfo.height)}");


    }

    /**
     * 获取上传凭证
     */
    private String getUploadToken(){

        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }
}
