package com.example.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_extra_payitem")
@Data
public class ExtraPayitem {

    private Integer id;

    private Integer  payitem_id;

    private String employee_id;

}
