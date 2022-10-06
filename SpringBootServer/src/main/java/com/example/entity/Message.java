package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@TableName("t_message")
//@AllArgsConstructor
@Data
public class Message{

    private Integer id; // 编号

    private String sendOpenID;

    private String receiverOpenId;

    private String msg;

    private Date time;

    private int ms_id;

    @TableField(select = false,exist = false)
    private String nickName;

    @TableField(select = false,exist = false)
    private String avatarUrl;

    @TableField(select = false,exist = false)
    private boolean mySelf;

    public Message(Integer id,String sendOpenID, String receiverOpenId, String msg, Date time,int ms_id) {
        this.id=id;
        this.sendOpenID=sendOpenID;
        this.receiverOpenId=receiverOpenId;
        this.msg=msg;
        this.time=time;
        this.ms_id=ms_id;
    }

//    public Message(Object id, String sender, String receiverOpenId, String msg, Date time) {
//    }
}
