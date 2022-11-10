package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.MsgSession;

import java.util.List;
import java.util.Map;

public interface MsgSessionService extends IService<MsgSession> {

    int add(MsgSession msgSession);

    MsgSession get(String openId1,String openId2);

    List<Map<String,Object>> getAllMsgSession(String openId);
}
