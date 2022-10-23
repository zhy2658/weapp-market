package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Notice;
import com.example.entity.Product;
import com.example.entity.R;
import com.example.service.MessageService;
import com.example.service.NoticeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice")
public class NoticeController {


    @Resource
    NoticeService noticeService;

    @RequestMapping("/getNew")
    public R getNew(){

        Notice notice = noticeService.getOne(
                new QueryWrapper<Notice>()
                        .orderByDesc("n_time")
                        .last("limit 1")
        );

        Map<String,Object> map=new HashMap<>();
        map.put("notice",notice);
        return R.ok(map);
    }

}
