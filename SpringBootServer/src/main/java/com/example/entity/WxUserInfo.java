package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 微信用户信息实体
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-01-08 15:59
 */
@TableName("t_wxuserinfo")
@Data
//@Getter
//@Setter
public class WxUserInfo implements Serializable {

    private Integer id; // 用户编号

    private String openid; // 用户唯一标识

    private String nickName; // 用户昵称

    private String avatarUrl; // 用户头像图片的 URL

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date registerDate; // 注册日期

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date lastLoginDate; // 最后登录日期

    private Integer sex;

    private String tags;

    private Integer isshow;  //0：不展示给别人的用户， 1：展示

    private Integer age;

    private String wxid;

    private Integer ustatus;

    private Integer small_id;

    private Integer admin;



//    小分类对象
    @TableField(select = false,exist = false)
    private SmallType smallType;

    @TableField(select = false,exist = false)
    private String code; // 微信用户code 前端传来的




}
