package com.example.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_publish_image")
@Data
public class PublishImg {
    private Integer id;

    private Integer sort;

    private String src;

    private Integer pid;

}
