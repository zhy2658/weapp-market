package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.mapper.PayItemMapper;
import com.example.properties.WeixinpayProperties;
import com.example.service.*;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private ExtraPayitemService extraPayitemService;

    @Resource
    private PayItemMapper payItemMapper;

    //获取我接受的订单
    // 订单状态 0 未支付 1等待员工接单 2 正在服务  3完成服务，待确认，4完成订单  5请求退单 6：已退单
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
//            1:  是员工未接单状态
            if (order.getStatus() != 1) {
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
            }

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

        resMap.put("totalPage", Math.ceil((float) total / pageSize));
        resMap.put("total", total);
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
        order.setStatus(3);
        orderService.updateById(order);

        return R.ok();
    }

    @RequestMapping("/employTakeOrder")
    public R employTakeOrders(@RequestParam("order_id") Integer orderId) {
        Order order = orderService.getById(orderId);
        OrderDetail orderDetail = orderDetailService.getOne(
                new QueryWrapper<OrderDetail>()
                        .eq("mId", order.getId())
        );
//        服务开始结束时间处理
        orderDetail.setServiceStart(new Date());
        orderDetail.setServiceEnd(new Date(System.currentTimeMillis()
                + 3600L * 1000 * orderDetail.getItemHours() * orderDetail.getGoodsNumber()));
        order.setStatus(2);
        orderService.updateById(order);
        orderDetailService.updateById(orderDetail);
        Map<String, Object> map = new HashMap<String, Object>();
        return R.ok(map);
    }

    @RequestMapping("/employTakeOrderByRandom")
    public R employTakeOrderByRandom(HttpServletRequest request, @RequestParam("order_id") Integer orderId) {

        String openId = (String) request.getSession().getAttribute("openId");
        WxUserInfo userInfo = iWxUserInfoService.findByOpenId(openId);
        Order order = orderService.getById(orderId);
        //判断是否有资格
        if ((order.getRandom_sex() == -1 || userInfo.getSex() == order.getRandom_sex())
                && userInfo.getEmployee_grade() >= order.getRandom_grade()) {
        } else {
            return R.error("抱歉！你没有资格接此单");
        }
        OrderDetail orderDetail = orderDetailService.getOne(
                new QueryWrapper<OrderDetail>()
                        .eq("mId", order.getId())
        );
//        服务开始结束时间处理
        orderDetail.setServiceStart(new Date());
        orderDetail.setServiceEnd(new Date(System.currentTimeMillis()
                + 3600L * 1000 * orderDetail.getItemHours() * orderDetail.getGoodsNumber()));
        order.setStatus(2);
        order.setServant_id(openId);
        orderDetail.setServant_id(openId);
        orderDetail.setGoodsPic(userInfo.getAvatarUrl());
        orderService.updateById(order);
        orderDetailService.updateById(orderDetail);
        Map<String, Object> map = new HashMap<String, Object>();
        return R.ok(map);
    }


    //创建服务项目
    @RequestMapping("/addExtreServiceItem")
    public R addExtreServiceItem(HttpServletRequest request, @RequestBody List<ExtraPayitem> extraPayitemList) {
        String openId = (String) request.getSession().getAttribute("openId");

        try {
            for(ExtraPayitem extraPayitem:extraPayitemList){
                extraPayitem.setEmployee_id(openId);
                extraPayitemService.save(extraPayitem);
            }
        } catch (Exception e) {
            System.out.println("sss");
            return R.error("请确认参数是否填写完整！");
        }
        return R.ok();
    }

    //删除服务项目


    //查询所有服务项目
    @RequestMapping("/getServiceItems")
    public R getServiceItems(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<>();
        String openId = (String) request.getSession().getAttribute("openId");

        WxUserInfo wxUserInfo = iWxUserInfoService.findByOpenId(openId);
        System.out.println(" wxUserInfo.getEmployee_grade()" + wxUserInfo.getEmployee_grade());
        List<PayItem> payItems = payItemService.list(
                new QueryWrapper<PayItem>()
                        .eq("grade", wxUserInfo.getEmployee_grade())
                        .eq("required", 0)
        );
        List<ExtraPayitem> extraPayitemList = extraPayitemService.list(
            new QueryWrapper<ExtraPayitem>()
                    .eq("employee_id",openId)
        );
        for(ExtraPayitem extraPayitem : extraPayitemList){
            PayItem payItem = payItemService.getById(extraPayitem.getPayitem_id());
            payItems.add(payItem);
        }
        System.out.println(payItems);

        resMap.put("payItems", payItems);

        return R.ok(resMap);
    }
    //查询所有服务项目
    @RequestMapping("/getExtraServiceItems")
    public R getExtraServiceItems(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<>();
        String openId = (String) request.getSession().getAttribute("openId");

        WxUserInfo wxUserInfo = iWxUserInfoService.findByOpenId(openId);
        List<PayItem> extraPayitemList = payItemService.list(
                new QueryWrapper<PayItem>()
                        .eq("grade", wxUserInfo.getEmployee_grade())
                        .eq("required", 1)
        );

        resMap.put("extraPayitemList", extraPayitemList);

        return R.ok(resMap);
    }

    //根据id删除服务项目
    @RequestMapping("/deleteExtraServiceItem")
    public R deleteExtraServiceItem(HttpServletRequest request,
                                @RequestParam("id") int id) {
        String openId = (String) request.getSession().getAttribute("openId");
        try {
            ExtraPayitem extraPayitem=extraPayitemService.getOne(
                    new QueryWrapper<ExtraPayitem>()
                            .eq("payitem_id",id)
                            .eq("employee_id",openId)
            );
            extraPayitemService.removeById(extraPayitem.getId());
        } catch (Exception e) {
            return R.error("删除失败，请确定id是否正确");
        }
        return R.ok();
    }

    //查询今日和本月接单量
    @RequestMapping("/getOrderCount")
    public R getOrderCount(HttpServletRequest request, @RequestBody(required = false)
    TecherTime techerTime) {
        Map<String, Object> resMap = new HashMap<>();
        String openId = (String) request.getSession().getAttribute("openId");

        System.out.println(techerTime);

        String begin = techerTime.getBegin();
        String end = techerTime.getEnd();

        //判断条件是否为空
//
//        if (!StringUtils.isEmpty(begin)){
//            wrapper.ge("gmt_create",begin);
//        }
//        if (!StringUtils.isEmpty(end)){
//            wrapper.le("gmt_create",end);
//        }
        Float todayRevenue = 0f;
        int todayOrder = orderService.count(
                new QueryWrapper<Order>()
                        .eq("servant_id", openId)
                        .ge("createDate", begin)
                        .le("createDate", end)
                        .and(
                                wp -> wp.eq("status", 2)
                                        .or()
                                        .eq("status", 3)
                                        .or()
                                        .eq("status", 4)
                        )
        );
        List<Order> orderList = orderService.list(
                new QueryWrapper<Order>()
                        .eq("servant_id", openId)
                        .ge("createDate", begin)
                        .le("createDate", end)
                        .and(
                                wp -> wp.eq("status", 2)
                                        .or()
                                        .eq("status", 3)
                                        .or()
                                        .eq("status", 4)
                        )


        );

        for (Order order : orderList) {
            todayRevenue += order.getTotalPrice().floatValue();
        }
//        eq("servant_id",openId)
//
//                .ge("createDate",begin)
//                .le("createDate",end)
//                .ne("status",1)

        resMap.put("todayOrder", todayOrder);
        resMap.put("todayRevenue", todayRevenue);
        return R.ok(resMap);
    }

    //    获取随机单
    @RequestMapping("/getRandomOrder")
    public R getRandomOrder(HttpServletRequest request,
                            @RequestParam("page") Integer page,
                            @RequestParam("pageSize") Integer pageSize) {

        String openId = (String) request.getSession().getAttribute("openId");

        Page<Order> pageOrder = new Page<>(page, pageSize); // 定义分页

        Page<Order> orderReslut = orderService.page(pageOrder,
                new QueryWrapper<Order>()
                        .eq("status", 1)
                        .eq("type", 1)
        );
        Integer total = orderService.getBaseMapper().selectCount(
                new QueryWrapper<Order>()
                        .eq("status", 1)
                        .eq("type", 1)
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

        resMap.put("totalPage", Math.ceil((float) total / pageSize));
        resMap.put("total", total);
        resMap.put("page", page);
        resMap.put("orderList", orderList);
        return R.ok(resMap);
    }


}
