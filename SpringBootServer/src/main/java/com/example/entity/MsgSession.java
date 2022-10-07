package com.example.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@TableName("t_msgsession")
@Data
public class MsgSession {

    private int ms_id;
    private String openId;
    private String openId2;

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date ms_time;

    public MsgSession( String openId, String openId2) {
        this.openId=openId;
        this.openId2=openId2;
    }
}
