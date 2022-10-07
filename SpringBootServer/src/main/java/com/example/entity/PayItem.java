package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_payitem")
@Data
public class PayItem implements Serializable {

    private Integer id;

    private String openId;

    private Integer pid;

    private Float price;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date create_time;

    private String itemName;

    private Integer itemHours;

}
