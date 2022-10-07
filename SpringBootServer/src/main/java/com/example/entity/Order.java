package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-01-12 10:13
 */
@TableName("t_order")
@Data
public class Order {

    private Integer id; // 编号

    private String orderNo; // 订单号

    private String userId; // openId微信用户ID

    @TableField(select = false)
    private WxUserInfo wxUserInfo; // 微信用户

    private BigDecimal totalPrice; // 总价

    private String address; // 收货地址

    private String consignee; // 收货人

    private String telNumber; // 联系电话

    private Integer pm_id;

    private String servant_id;   //服务人

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date createDate; // 订单创建日期

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date payDate; // 订单支付日期

    private Integer status=1; // 订单状态 1 未支付 2 已经支付/待收货 3 退款/退货

    @TableField(select = false,exist = false)
    private OrderDetail[] goods; // 订单商品详情

}
