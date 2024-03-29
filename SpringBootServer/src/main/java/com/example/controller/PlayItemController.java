package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.ExtraPayitem;
import com.example.entity.PayItem;
import com.example.entity.R;
import com.example.entity.WxUserInfo;
import com.example.service.*;
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

    @Resource
    ExtraPayitemService extraPayitemService;

    @RequestMapping("/getByGrade")
    public R getByGrade(@RequestParam("grade") String grade,@RequestParam("openId") String openId){

        List<PayItem> payitemList = payItemService.list(new QueryWrapper<PayItem>().eq("grade",grade));
        List<ExtraPayitem> extraPayitemList=extraPayitemService.list(
                new QueryWrapper<ExtraPayitem>()
                        .eq("employee_id",openId)
        );
        for(ExtraPayitem extraPayitem:extraPayitemList){
            PayItem tempPayItem=payItemService.getById(extraPayitem.getPayitem_id());
            payitemList.add(tempPayItem);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("payitemList",payitemList);
        return R.ok(map);
    }
    @RequestMapping("/getByAllGrade")
    public R getByAllGrade(@RequestParam("grade") String grade){
        List<PayItem> payitemList = payItemService.list(new QueryWrapper<PayItem>().eq("grade",grade));
        Map<String,Object> map=new HashMap<>();
        map.put("payitemList",payitemList);
        return R.ok(map);
    }
}
