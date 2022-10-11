package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_publish_like")
@Data
public class PublishLike implements Serializable {

    private Integer id;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date ltime;

    private Integer pid;

    private String openId;

    @TableField(select = false)
    private WxUserInfo userInfo;       //用户
}
