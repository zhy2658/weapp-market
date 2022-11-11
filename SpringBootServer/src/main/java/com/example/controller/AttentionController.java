package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.service.AttentionService;
import com.example.service.IProductService;
import com.example.service.IWxUserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attention")
public class AttentionController {

    @Resource
    IWxUserInfoService wxUserInfoService;

    @Resource
    AttentionService attentionService;

    @Resource
    IProductService iproductService;


    @RequestMapping("/list")
    public R list(HttpServletRequest request){
        String openId = (String) request.getSession().getAttribute("openId");

        List<Attention> attentionList = attentionService.list(
                new QueryWrapper<Attention>()
                        .eq("user_id",openId)
        );
        List<WxUserInfo> userInfoList = new ArrayList<>();
        for(Attention attention : attentionList) {
            WxUserInfo userInfo = wxUserInfoService.findByOpenId(attention.getOpposite_id());
            Product product=iproductService.getOne(
                    new QueryWrapper<Product>()
                            .eq("openId",userInfo.getOpenid())
            );
            Product newProduct=new Product();
            newProduct.setId(product.getId());
            userInfo.setDetail(newProduct);
            userInfoList.add(userInfo);
        }

        Map<String,Object> map=new HashMap<>();
        map.put("userInfoList",userInfoList);
        return R.ok(map);
    }
    @RequestMapping("/remove")
    public R remove(HttpServletRequest request,@RequestParam("opposite_id") String opposite_id){
        String openId = (String) request.getSession().getAttribute("openId");
        attentionService.remove(
                new QueryWrapper<Attention>()
                        .eq("user_id",openId)
                        .eq("opposite_id",opposite_id)
        );

        Map<String,Object> map=new HashMap<>();
        return R.ok(map);
    }
    @RequestMapping("/add")
    public R add(HttpServletRequest request,@RequestParam("opposite_id") String opposite_id){
        String openId = (String) request.getSession().getAttribute("openId");
        int isExist=attentionService.count(
                new QueryWrapper<Attention>()
                        .eq("user_id",openId)
                        .eq("opposite_id",opposite_id)
        );
        if(isExist==0){
            Attention attention =new Attention();
            attention.setUser_id(openId);
            attention.setOpposite_id(opposite_id);
            attentionService.save(attention);
        }
        Map<String,Object> map=new HashMap<>();
        return R.ok(map);
    }




}
