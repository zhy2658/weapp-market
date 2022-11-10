package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_payitem")
@Data
public class PayItem implements Serializable {

    private Integer id;

//    private String openId;

//    private Integer pid;

    private Float price;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date create_time;

    private String itemName;

    private Integer itemHours;

    private Integer grade;  //1级雇员  ，2:2级雇员   ...以此类推

    private Integer required;   //是否必选    0:必选    1:可选

}
