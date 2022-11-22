package com.example.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.service.IOrderDetailService;
import com.example.service.IOrderService;
import com.example.service.IWxUserInfoService;
import com.example.service.TopupRecordService;
import net.sf.jsqlparser.statement.select.Top;
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
@RequestMapping("/admin/topup")
public class AdminTopupRecordController {

    @Autowired
    private TopupRecordService topupRecordService;

    @Resource
    private IWxUserInfoService iWxUserInfoService;

    /**
     * 根据条件分页查询
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        int total =topupRecordService.count(
                new QueryWrapper<TopupRecord>()
                        .like("topupNo",pageBean.getQuery())
        );
        Page<TopupRecord> page=new Page<>(pageBean.getPageNum(),pageBean.getPageSize());
        Page<TopupRecord> pageResult = topupRecordService.page(page,
                new QueryWrapper<TopupRecord>()
                        .like("topupNo",pageBean.getQuery())
                        .orderByDesc("create_time")

        );
        List<TopupRecord> list = pageResult.getRecords();
        for(TopupRecord topupRecord:list ){
            WxUserInfo wxUserInfo= iWxUserInfoService.getOne(
                     new QueryWrapper<WxUserInfo>()
                             .eq("openid",topupRecord.getOpenId())
            );
            topupRecord.setWxUserInfo(wxUserInfo);
        }

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("orderList",list);
        resultMap.put("total",total);
        return R.ok(resultMap);
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
