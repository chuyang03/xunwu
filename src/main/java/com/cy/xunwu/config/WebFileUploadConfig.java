package com.cy.xunwu.config;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

/**
 * 文件上传配置
 *
 */

@Configuration
@ConditionalOnClass({Servlet.class, StandardServletMultipartResolver.class, MultipartConfigElement.class})
@ConditionalOnProperty(prefix = "spring.servlet.multipart", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class)
public class WebFileUploadConfig {

    //下面两行表示给当前类注入一个属性
    private final MultipartProperties multipartProperties;

    public WebFileUploadConfig(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }

    /**
     * 上传配置
     */

    @Bean
    @ConditionalOnMissingBean
    public MultipartConfigElement multipartConfigElement(){

        return this.multipartProperties.createMultipartConfig();
    }

    /**
     * 注册解析器
     * 如果容器中没有视图解析器，那么在此注入一个视图解析器
     */
    @Bean
    @ConditionalOnMissingBean(MultipartResolver.class)
    public StandardServletMultipartResolver multipartResolver(){

        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        //给自定义的视图解析器设置一些属性
        multipartResolver.setResolveLazily(this.multipartProperties.isResolveLazily());

        return multipartResolver;
    }

    /**
     * 七牛云
     * 华东机房
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuCofig(){

        return new com.qiniu.storage.Configuration(Zone.zone0());
    }

    /**
     * 构建一个七牛云上传工具实例
     */
    @Bean
    public UploadManager uploadManager(){

        return new UploadManager(qiniuCofig());
    }

    @Value("${qiniu.AccessKey}")
    private String accessKey;
    @Value("${qiniu.SecretKey}")
    private String secretKey;

    /**
     * 认证信息实例
     */
    @Bean
    public Auth auth(){

        return Auth.create(accessKey, secretKey);
    }

    /**
     * 构建七牛云空间管理实例
     */
    @Bean
    public BucketManager bucketManager(){

        return new BucketManager(auth(), qiniuCofig());
    }

    @Bean
    public Gson gson(){

        return new Gson();
    }
}
