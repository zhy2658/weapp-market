package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.models.auth.In;
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

    private Integer order_count;  //第几次下同样的单子

    private Integer type; // 0:指定单，  1:随机单

    private Integer random_sex;    //性别可以接的随机单   1：女    2：男

    private Integer random_grade;   //至少要几级员工才可以接这个单子

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date createDate; // 订单创建日期

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date payDate; // 订单支付日期

    private Integer status=0; // 订单状态 0 未支付 1等待员工接单 2 正在服务  3完成服务，待确认，4完成订单  5请求退单 6：已退单

   private String remark;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
   private Date finishDate;

    @TableField(select = false,exist = false)
    private OrderDetail[] goods; // 订单商品详情

}
