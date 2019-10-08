package com.cy.xunwu.web.dto;

/**
 *
 * 七牛云上传图片的结果集
 */
public class QiNiuPutResult {

    public String key;
    public String hash;
    public String bucket;

    public int width;
    public int height;

    @Override
    public String toString() {
        return "QiNiuPutResult{" +
                "key='" + key + '\'' +
                ", hash='" + hash + '\'' +
                ", bucket='" + bucket + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
