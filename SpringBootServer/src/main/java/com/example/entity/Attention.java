package com.example.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@TableName("attention")
@Data
public class Attention {

    private Integer id;

    private String user_id;

    private String opposite_id;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date attention_time;
    
    
    
}
