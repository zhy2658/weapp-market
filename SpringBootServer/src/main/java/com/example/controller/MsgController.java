package com.example.controller;

import com.example.entity.Message;
import com.example.handler.WebSocketServer;
import com.example.service.MessageService;
import com.example.service.MsgSendService;
import com.example.handler.WebSocketServer;
import com.example.service.MsgSendService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yunji
 */
@Slf4j
@Api(tags = "消息接口")
@RequiredArgsConstructor
@RestController
public class MsgController {

    private final MsgSendService msgSendService;
    private final WebSocketServer webSocketServer;

    @Resource
    MessageService messageService;


    @ApiOperation("发送消息")
    @GetMapping("send")
    public String send(String msg,String openId,HttpServletRequest request) {
//        System.out.println(openId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg",msg);
        map.put("date", LocalDateTime.now().toString());
        map.put("receiverOpenId",openId);
        int size = msgSendService.sendMsg(map,request);
        webSocketServer.sendToAll(map);
        return "发送成功" + size;
    }




}
