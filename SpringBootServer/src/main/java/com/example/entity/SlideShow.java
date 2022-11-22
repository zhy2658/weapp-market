package com.example.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@TableName("t_slideshow")
@Data
public class SlideShow {

    private Integer id;

    private Integer type;

    private String appPage;

    private String imgSrc;

    private  String  arg;

    private Integer employeeId;

    private String url;

    private Integer sort;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date create_time;

}
