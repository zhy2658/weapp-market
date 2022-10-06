package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-22 22:13
 */
@TableName("t_product")
@Data
public class Product implements Serializable {

    private Integer id; // 编号

    private String name; // 名称

    private BigDecimal price; // 价格

    private String productIntroImgs; // 商品介绍图片

    private String productParaImgs;  // 商品规格参数图片

    private Integer stock; // 库存

    private String proPic="default.jpg"; // 商品图片

    private boolean isHot=false; // 是否热门推荐商品

    private boolean isSwiper=false; // 是否轮播图片商品

    private Integer swiperSort=0; // 轮播排序

    private String swiperPic="default.jpg"; // 商品轮播图片

    private String description; // 描述

    private String openId;

    private String audio;  //音频

    private int audioTime; //音频秒数

    @TableField(select = false)
    private SmallType type; // 商品类比

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    @TableField(updateStrategy  = FieldStrategy.IGNORED)
    private Date hotDateTime; // 设置热门推荐日期时间

    @TableField(select = false)
    private List<ProductSwiperImage> productSwiperImageList;  // 商品轮播图片

    @TableField(select = false)
    private WxUserInfo userInfo;  // 商品轮播图片



}
