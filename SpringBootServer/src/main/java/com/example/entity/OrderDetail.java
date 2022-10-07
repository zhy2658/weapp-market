package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详细表
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-01-12 10:19
 */
@TableName("t_order_detail")
@Data
public class OrderDetail {

    private Integer id; // 编号

    private Integer mId; // 订单主表Id

    private Integer goodsId; // 商品ID

    private Integer goodsNumber; // 商品购买数量

    private BigDecimal goodsPrice; // 商品单价

    private String goodsName; // 商品名称

    private String goodsPic; // 商品图片

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date serviceStart; //开始服务时间

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date serviceEnd;    //结束时间

    private int totalHours;  //总时长

    private int itemHours; //单时长

    private Float totalPrice; //总价

    private String servant_id;   //服务人




    @TableField(select = false,exist = false)
    private int restDay;   //剩下多少天
    @TableField(select = false,exist = false)
    private int restHours;   //剩下多少小时
    @TableField(select = false,exist = false)
    private float finishedPersent;  //完成进度
    @TableField(select = false,exist = false)
    private WxUserInfo wxUserInfo;  //支付用户

}
