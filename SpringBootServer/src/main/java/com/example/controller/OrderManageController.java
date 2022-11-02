package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.mapper.PayItemMapper;
import com.example.properties.WeixinpayProperties;
import com.example.service.IOrderDetailService;
import com.example.service.IOrderService;
import com.example.service.IWxUserInfoService;
import com.example.service.PayItemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Array;
import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("/order/manage/")
public class OrderManageController {


    @Resource
    private IOrderService orderService;

    @Resource
    private IOrderDetailService orderDetailService;

    @Resource
    private WeixinpayProperties weixinpayProperties;

    @Resource
    private IWxUserInfoService iWxUserInfoService;

    @Resource
    private PayItemService payItemService;

    @Resource
    private PayItemMapper payItemMapper;

    //获取我接受的订单
    // 订单状态 0 未支付 1 已经支付，正在服务  2完成服务，待确认，3完成交易  4：已退单
    @RequestMapping("/getMyOrder")
    public R getMyOrder(HttpServletRequest request,
                        @RequestParam("status") int status,
                        @RequestParam("page") Integer page,
                        @RequestParam("pageSize") Integer pageSize) {

        String openId = (String) request.getSession().getAttribute("openId");

        Page<Order> pageOrder = new Page<>(page, pageSize); // 定义分页

        Page<Order> orderReslut = orderService.page(pageOrder,
                new QueryWrapper<Order>()
                        .eq("status", status)
                        .eq("servant_id", openId)
        );
        Integer total = orderService.getBaseMapper().selectCount(
                new QueryWrapper<Order>()
                        .eq("status", status)
                        .eq("servant_id", openId)
        );
        List<Order> orderList = orderReslut.getRecords();
        for (Order order : orderList) {
//            System.out.println(order);
            List<OrderDetail> orderDetailList = orderDetailService.list(
                    new QueryWrapper<OrderDetail>()
                            .eq("mId", order.getId())
            );
            OrderDetail[] orderDetails = new OrderDetail[1];
            OrderDetail orderDetail = orderDetailList.get(0);
            Date startDate = orderDetail.getServiceStart();
            Date endDate = orderDetail.getServiceEnd();
//            System.out.println("date================"+orderDetail.getServiceStart());
            int restDay = (int) (endDate.getTime() - new Date().getTime()) / (1000 * 3600 * 24);
            int restHours = (int) (endDate.getTime() - new Date().getTime()) / (1000 * 3600);
            int restMinutes = (int) (endDate.getTime() - new Date().getTime()) / (1000 * 60);
//            System.out.println(restHours+"---"+orderDetail.getTotalHours());
            orderDetail.setFinishedPersent(1f - ((float) restMinutes / (orderDetail.getTotalHours() * 60)));
            DecimalFormat df = new DecimalFormat("0.00");
            orderDetail.setFinishedPersent(Float.parseFloat(df.format(orderDetail.getFinishedPersent())));
            orderDetail.setFinishedPersent(
                    (orderDetail.getFinishedPersent() > 1) ? 1 : orderDetail.getFinishedPersent()
            );
            orderDetail.setRestHours(restHours > 0 ? (restHours % 24) : 0);
            orderDetail.setRestDay(restDay);
            orderDetail.setRestMinutes(restMinutes > 0 ? (restMinutes % 60) : 0);
            orderDetails[0] = orderDetail;
            order.setGoods(orderDetails);

//            支付人
            WxUserInfo wxUserInfo = iWxUserInfoService.getOne(
                    new QueryWrapper<WxUserInfo>()
                            .eq("openid", order.getUserId())
            );
            order.setWxUserInfo(wxUserInfo);
        }

        Map<String, Object> resMap = new HashMap<String, Object>();

        resMap.put("totalPage",Math.ceil( (float)total/ pageSize));
        resMap.put("total",total);
        resMap.put("page", page);
        resMap.put("orderList", orderList);
        return R.ok(resMap);
    }

    //确认完成服务  状态码更新为 2
    @RequestMapping("/confirmFinishedService")
    public R confirmFinishedService(HttpServletRequest request, @RequestParam("order_id") int order_id) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Order order = orderService.getById(order_id);
        OrderDetail orderDetail = orderDetailService.getOne(
                new QueryWrapper<OrderDetail>().eq("mId", order.getId())
        );
        Date now = new Date();
        if (now.getTime() < orderDetail.getServiceEnd().getTime()) {
            return R.error("当前还不能确认完成服务");
        }
        System.out.println(order);
        order.setStatus(2);
        orderService.updateById(order);

        return R.ok();
    }

    //创建服务项目
//    @RequestMapping("/createServiceItem")
//    public R createServiceItem(HttpServletRequest request, PayItem payItem) {
//        String openId = (String) request.getSession().getAttribute("openId");
//        payItem.setOpenId(openId);
//        try {
//            payItemService.save(payItem);
//        } catch (Exception e) {
//            System.out.println("sss");
//            return R.error("请确认参数是否填写完整！");
//        }
//        return R.ok();
//    }

    //删除服务项目


    //查询所有服务项目
//    @RequestMapping("/getServiceItems")
//    public R getServiceItems(HttpServletRequest request) {
//        Map<String, Object> resMap = new HashMap<>();
//        String openId = (String) request.getSession().getAttribute("openId");
//        try {
//            List<PayItem> payItems = payItemService.list(
//                    new QueryWrapper<PayItem>()
//                            .eq("openId", openId)
//            );
//            resMap.put("payItems", payItems);
//        } catch (Exception e) {
//            return R.error("token失效，请重新登录！");
//        }
//        return R.ok(resMap);
//    }

    //根据id删除服务项目
    @RequestMapping("/deleteServiceItems")
    public R deleteServiceItems(HttpServletRequest request,
                                @RequestParam("payitem_id") int payitem_id) {
        try {
            System.out.println(payitem_id);
            payItemMapper.deleteById(payitem_id);
        } catch (Exception e) {
            return R.error("删除失败，请确定id是否正确");
        }
        return R.ok();
    }

}
