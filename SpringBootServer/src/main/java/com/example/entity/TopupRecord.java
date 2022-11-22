package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("t_topup_record")
@Data
public class TopupRecord implements Serializable {

    private Integer id;

    private String topupNo;

    private String openId;

    private Float coinNum;

    private BigDecimal totalPrice;

    private Integer status;   //0:未支付   1：支付成功

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date create_time;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date payDate;

    @TableField(select = false,exist = false)
    private WxUserInfo wxUserInfo;   //老密码

}
