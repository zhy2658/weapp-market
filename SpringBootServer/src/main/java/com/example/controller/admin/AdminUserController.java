package com.example.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.PageBean;
import com.example.entity.R;
import com.example.entity.WxUserInfo;
import com.example.service.IWxUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理端-用户Controller控制器
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-09 6:48
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private IWxUserInfoService wxUserInfoService;


    private final static Logger logger= LoggerFactory.getLogger(AdminUserController.class);

    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
//        System.out.println("----====="+pageBean);
//        System.out.println(pageBean);
        String query=pageBean.getQuery().trim();
        Page<WxUserInfo> page=new Page<>(pageBean.getPageNum(),pageBean.getPageSize());
        Page<WxUserInfo> pageResult = wxUserInfoService.page(page, new QueryWrapper<WxUserInfo>().like("nickName", query));
        Map<String,Object> map=new HashMap<>();
        map.put("userList",pageResult.getRecords());
        map.put("total",pageResult.getTotal());
        return R.ok(map);
    }

    @RequestMapping("/update_isshow")
    public R updateUserShow(@RequestParam("openId") String openId,@RequestParam("isshow") int isshow){
        wxUserInfoService.updateUserShow(openId,isshow);
        Map<String,Object> map=new HashMap<>();
        map.put("code", 0);
        map.put("msg", "更新成功");
        return R.ok(map);
    }

}
