package com.cy.xunwu.web.dto;

import java.util.List;

/**
 *
 * 通用多结果service返回结构
 */
public class ServiceMultiResult<T> {

    //结果集总记录数
    private long total;

    //结果集
    private List<T> result;

    public ServiceMultiResult(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    //获取结果记录数
    public int getResultSize(){

        if (this.result == null){
            return 0;
        }

        return this.result.size();
    }
}
