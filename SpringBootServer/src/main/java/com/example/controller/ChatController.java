package com.example.controller;


import com.example.entity.Message;
import com.example.entity.MsgSession;
import com.example.handler.WebSocketServer;
import com.example.service.MessageService;
import com.example.service.MsgSendService;
import com.example.service.MsgSessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat/")
public class ChatController {

    @Resource
    MessageService messageService;

    @Resource
    MsgSessionService msgSessionService;

    @GetMapping("/getMessage")
    public List<Message> getMessage(String openId, HttpServletRequest request) {
        Map res=new HashMap<String,Object>();

        List<Message> list = messageService.getMessage(request,openId);
        res.put("messages",list);

        return list;
    }
    @RequestMapping("/getAllMsgSession")
    public List<Map<String,Object>> getAllMsgSession( HttpServletRequest request) {
        String openId = (String) request.getSession().getAttribute("openId");
        return msgSessionService.getAllMsgSession(openId);
    }



}
