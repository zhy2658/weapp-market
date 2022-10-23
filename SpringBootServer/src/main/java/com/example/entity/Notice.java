package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@TableName("t_notice")
@Data
public class Notice {

    private Integer id;

    private String title;

    private String message;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date n_time;

    private Integer top;

}
