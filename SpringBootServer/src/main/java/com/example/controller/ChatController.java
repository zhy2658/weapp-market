package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Message;
import com.example.entity.MsgSession;
import com.example.entity.Order;
import com.example.entity.R;
import com.example.handler.WebSocketServer;
import com.example.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat/")
public class ChatController {

    @Resource
    MessageService messageService;

    @Resource
    MsgSessionService msgSessionService;

    @Resource
    IOrderService iOrderService;

    @Resource
    IOrderDetailService iOrderDetailService;

    @GetMapping("/getMessage")
    public List<Message> getMessage(String openId, HttpServletRequest request) {
//        String UserOpenId = (String) request.getSession().getAttribute("openId");

        List<Message> messageList = messageService.getMessage(request,openId);

        Map res=new HashMap<String,Object>();
        res.put("messageList",messageList);
        return messageList;
    }
    @GetMapping("/judgeChatEnd")
    public R judgeChatEnd(String openId, HttpServletRequest request) {
        String UserOpenId = (String) request.getSession().getAttribute("openId");

        List<Order> orderList=iOrderService.list(
                new QueryWrapper<Order>()
                        .eq("userId",UserOpenId)
                        .eq("servant_id",openId)
                        .eq("status",2)

        );
        List<Order> orderList2=iOrderService.list(
                new QueryWrapper<Order>()
                        .eq("userId",openId)
                        .eq("servant_id",UserOpenId)
                        .eq("status",2)

        );
        int count = orderList.size()+orderList2.size();
//        System.out.println(count+"------------------===");
        Map res=new HashMap<String,Object>();
        res.put("isOrderEnd",count>0?false:true);
        return R.ok(res);
    }



    @RequestMapping("/getAllMsgSession")
    public List<Map<String,Object>> getAllMsgSession( HttpServletRequest request) {
        String openId = (String) request.getSession().getAttribute("openId");
        List<Map<String,Object>> mapList=msgSessionService.getAllMsgSession(openId);

        for( Map<String,Object> map : mapList){

//            判断服务时间是否到了
            List<Order> orderList=iOrderService.list(
                    new QueryWrapper<Order>()
                            .eq("userId",map.get("openId"))
                            .eq("servant_id",openId)
                            .eq("status",2)

            );
            List<Order> orderList2=iOrderService.list(
                    new QueryWrapper<Order>()
                            .eq("userId",openId)
                            .eq("servant_id",map.get("openId"))
                            .eq("status",2)

            );
            int count = orderList.size()+orderList2.size();

            map.put("isOrderEnd",count>0?false:true);

            //寻找最新一条信息
            List<Message> messageList=messageService.list(
                    new QueryWrapper<Message>()
                            .eq("ms_id",map.get("ms_id"))
                            .orderByDesc("time")
                            .last("limit 1")
            );
            if(messageList.size()>0) map.put("message",messageList.get(0));
            else map.put("message", new Message());

        }
        return mapList;
    }



}
