package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_publish_like")
@Data
public class PublishLike implements Serializable {

    private int id;

    private Date ltime;

    private int pid;

    private String openId;

    @TableField(select = false)
    private WxUserInfo userInfo;       //用户
}
