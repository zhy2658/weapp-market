package com.example.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 分页Model类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company Java知识分享网
 * @create 2020-02-16 上午 11:05
 */
@Data
public class PageBean {


    private int pageNum; // 第几页
    private int pageSize; // 每页记录数

    private int start;  // 起始页
    private String query; // 查询参数


    private static final long serialVersionUID = 2L;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换


    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;


    private Integer status;  //状态



    public PageBean() {
    }

    public PageBean(int pageNum, int pageSize, String query) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.query = query;
    }

    public PageBean(int pageNum, int pageSize) {
        super();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (pageNum-1)*pageSize;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
