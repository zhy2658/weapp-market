package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Message;
import com.example.entity.MsgSession;
import com.example.mapper.MessageMapper;
import com.example.mapper.MsgSessionMapper;
import com.example.service.MsgSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("MsgSessionService")
public class MsgSessionServiceImpl extends ServiceImpl<MsgSessionMapper, MsgSession>  implements MsgSessionService {

    @Resource
    MsgSessionMapper msgSessionMapper;

    @Override
    public int add(MsgSession msgSession) {
        return msgSessionMapper.add(msgSession);
    }

    @Override
    public MsgSession get(String openId1, String openId2) {

        return  msgSessionMapper.get(openId1,openId2);
    }

    @Override
    public List<Map<String,Object>> getAllMsgSession(String openId) {
        List<Map<String,Object>> list =msgSessionMapper.getAllMsgSession(openId,openId,openId);
        for(Map<String,Object> map: list){
            map.remove("openid");
            String removeKey = null;
            for (String key : map.keySet()) {
                if(map.get(key).equals(openId)) removeKey=key;
            }
            map.remove(removeKey);
            if (removeKey.equals("openId")){
                String oppsiteOpenId=(String) map.get("openId2");
                map.remove("openId2");
                map.put("openId",oppsiteOpenId);
            }
        }

        return list;
    }
}
