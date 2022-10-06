package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Message;
import com.example.entity.WxUserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MessageService extends IService<Message> {

    Integer addMessage(Message message);

    List<Message> getMessage(HttpServletRequest request, String oppositeOpenId);
}
