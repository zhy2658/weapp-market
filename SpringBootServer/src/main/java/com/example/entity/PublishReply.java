package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_publish_reply")
@Data
public class PublishReply implements Serializable {

    private Integer id;

    private Integer pid;

    private String openId;

    private String msg;

    private Date retime;

    @TableField(select = false)
    private WxUserInfo userInfo;       //用户
}
