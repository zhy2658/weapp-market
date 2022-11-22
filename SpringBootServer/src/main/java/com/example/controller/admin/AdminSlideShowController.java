package com.example.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.service.IWxUserInfoService;
import com.example.service.SlideShowService;
import com.example.service.TopupRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理端-订单Controller控制器
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-17 7:27
 */
@RestController
@RequestMapping("/admin/slideshow")
public class AdminSlideShowController {

    @Autowired
    private TopupRecordService topupRecordService;

    @Resource
    private IWxUserInfoService iWxUserInfoService;

    @Resource
    private SlideShowService slideShowService;

    /**
     * 根据条件分页查询
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        int total =slideShowService.count(
                new QueryWrapper<SlideShow>()
        );
        Page<SlideShow> page=new Page<>(pageBean.getPageNum(),pageBean.getPageSize());
        Page<SlideShow> pageResult = slideShowService.page(page,
                new QueryWrapper<SlideShow>()
                        .orderByDesc("create_time")

        );
        List<SlideShow> list = pageResult.getRecords();
//        for(TopupRecord topupRecord:list ){
//            WxUserInfo wxUserInfo= iWxUserInfoService.getOne(
//                     new QueryWrapper<WxUserInfo>()
//                             .eq("openid",topupRecord.getOpenId())
//            );
//            topupRecord.setWxUserInfo(wxUserInfo);
//        }

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("slideShowList",list);
        resultMap.put("total",total);
        return R.ok(resultMap);
    }

    @RequestMapping("/{id}")
    public R findById(@PathVariable(value = "id") Integer id){
        SlideShow notice = slideShowService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("notice",notice);
        return R.ok(map);
    }

    @PostMapping("/save")
    public R save(@RequestBody SlideShow slideShow){
        if(slideShow.getId()==null || slideShow.getId()==-1){
            slideShowService.save(slideShow);
        }else{
            slideShowService.saveOrUpdate(slideShow);
        }
        return R.ok();
    }

    @PostMapping("/saveSwiper")
    public R saveSwiper(@RequestBody SlideShow slideShow){
        SlideShow p = slideShowService.getById(slideShow.getId());
        p.setImgSrc(slideShow.getImgSrc());
        p.setSort(slideShow.getSort());
        slideShowService.saveOrUpdate(p);
        return R.ok();
    }
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){

        slideShowService.removeById(id);
        return R.ok();
    }

    /**
     * 更新订单状态
     * @param order
     * @return
//     */
//    @PostMapping("/updateStatus")
//    public R updateStatus(@RequestBody Order order){
//        Order resultOrder = orderService.getById(order.getId());
//        resultOrder.setStatus(order.getStatus());
//        orderService.saveOrUpdate(resultOrder);
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     * @param id
//     * @return
//     */
//    @GetMapping("/delete/{id}")
//    public R delete(@PathVariable(value = "id") Integer id){
//        // 删除订单细表的数据
//        orderDetailService.remove(new QueryWrapper<OrderDetail>().eq("mId",id));
//        orderService.removeById(id);
//        return R.ok();
//    }

}
