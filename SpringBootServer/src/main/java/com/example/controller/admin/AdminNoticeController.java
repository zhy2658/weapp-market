package com.example.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    @Resource
    NoticeService noticeService;



    @RequestMapping("/list")
    public R allAdmin(@RequestBody PageBean pageBean){
//        System.out.println("----====="+pageBean);
//        System.out.println(pageBean);
        String query=pageBean.getQuery().trim();
        Page<Notice> page=new Page<>(pageBean.getPageNum(),pageBean.getPageSize());
        Page<Notice> pageResult = noticeService.page(page,
                new QueryWrapper<Notice>()
                        .like("title", query)
        );
        Map<String,Object> map=new HashMap<>();
        map.put("notieList",pageResult.getRecords());
        map.put("total",pageResult.getTotal());
        return R.ok(map);
    }
    @RequestMapping("/add")
    public R add(@RequestBody Notice notice){

        try{
            noticeService.save(notice);
        }catch (Exception e){
            return R.error("插入失败");
        }

        Map<String,Object> map=new HashMap<>();
//        map.put("total",pageResult.getTotal());
        return R.ok(map);
    }

    @PostMapping("/save")
    public R save(@RequestBody Notice notice){
        if(notice.getId()==null || notice.getId()==-1){
            noticeService.save(notice);
        }else{
            noticeService.saveOrUpdate(notice);
        }
        return R.ok();
    }

    @RequestMapping("/{id}")
    public R findById(@PathVariable(value = "id") Integer id){
        Notice notice = noticeService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("notice",notice);
        return R.ok(map);
    }


}
