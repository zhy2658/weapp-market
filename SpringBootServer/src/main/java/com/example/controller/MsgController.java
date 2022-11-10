package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Message;
import com.example.entity.MsgSession;
import com.example.entity.R;
import com.example.handler.WebSocketServer;
import com.example.service.MessageService;
import com.example.service.MsgSendService;
import com.example.handler.WebSocketServer;
import com.example.service.MsgSendService;
import com.example.service.MsgSessionService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    MsgSessionService msgSessionService;


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


    @GetMapping("/removeItem")
    public R removeItem(@RequestParam("ms_id") String ms_id, HttpServletRequest request) {
        String openId = (String) request.getSession().getAttribute("openId");
//        System.out.println(openId);

        MsgSession msgSession=msgSessionService.getOne(
                new QueryWrapper<MsgSession>()
                        .eq("ms_id",ms_id)
                        .eq("openId",openId)
        );
        msgSession.setIsShow(0);
        msgSessionService.update(msgSession,
                new QueryWrapper<MsgSession>()
                .eq("ms_id",msgSession.getMs_id() )
        );

        Map<String,Object> map=new HashMap<>();
        return R.ok(map);
    }

    @GetMapping("/readMsg")
    public R readMsg(@RequestParam("id") Integer id, HttpServletRequest request) {

        Message message=new Message();
        message.setId(id);
        message.setIsRead(1);
        messageService.updateById(message);
        return R.ok();
    }




}
