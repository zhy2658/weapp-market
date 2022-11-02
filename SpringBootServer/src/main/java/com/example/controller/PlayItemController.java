package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.PayItem;
import com.example.entity.R;
import com.example.entity.WxUserInfo;
import com.example.service.IProductService;
import com.example.service.IProductSwiperImageService;
import com.example.service.IWxUserInfoService;
import com.example.service.PayItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/playitem")
public class PlayItemController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductSwiperImageService productSwiperImageService;

    @Resource
    private IWxUserInfoService iWxUserInfoService;

    @Resource
    private PayItemService payItemService;

    @RequestMapping("/getByGrade")
    public R getByGrade(@RequestParam("grade") String grade){

        List<PayItem> payitemList = payItemService.list(new QueryWrapper<PayItem>().eq("grade",grade));
        Map<String,Object> map=new HashMap<>();
        map.put("payitemList",payitemList);
        return R.ok(map);
    }
}
