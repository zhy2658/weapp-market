package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Message;
import com.example.entity.MsgSession;
import com.example.entity.Order;
import com.example.entity.WxUserInfo;
import com.example.mapper.MessageMapper;
import com.example.mapper.OrderMapper;
import com.example.mapper.WxUserInfoMapper;
import com.example.service.MessageService;
import com.example.service.MsgSessionService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service("MessageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private WxUserInfoMapper wxUserInfoMapper;

    @Resource
    MsgSessionService msgSessionService;

    @Override
    public Integer addMessage(Message message) {
        return messageMapper.addMessage(message);
    }

    @Override
    public List<Message> getMessage(HttpServletRequest request, String oppositeOpenId) {
        HttpSession session = request.getSession();
        Claims claims = (Claims) session.getAttribute("claims");
        Map map= new HashMap<String,Object>();
        map.put("myOpenId",claims.getId());
        map.put("oppositeOpenId",oppositeOpenId);
        List<Message> list1= messageMapper.getSenderMessage(map);
        List<Message> list2= messageMapper.getReceiverMessage(map);
        list1.addAll(list2);
        list1.sort(
                (t1, t2) -> t2.getTime().compareTo(t1.getTime())
        );
        Collections.reverse(list1);

        WxUserInfo wxUserInfo = wxUserInfoMapper.findByOpenId(oppositeOpenId);
        for(Message msgItem :list1){
            if( msgItem.getSendOpenID().equals(wxUserInfo.getOpenid()) ){
                msgItem.setNickName(wxUserInfo.getNickName());
                msgItem.setAvatarUrl(wxUserInfo.getAvatarUrl());

            } else if (msgItem.getSendOpenID().equals(claims.getId())) {
                msgItem.setNickName(claims.getSubject());
                msgItem.setMySelf(true);

            }
        }

        return list1;
    }
}
