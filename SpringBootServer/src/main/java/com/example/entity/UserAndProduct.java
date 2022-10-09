package com.example.entity;

import lombok.Data;

@Data
public class UserAndProduct {

    private  WxUserInfo userInfo;

    private Product product;
}
