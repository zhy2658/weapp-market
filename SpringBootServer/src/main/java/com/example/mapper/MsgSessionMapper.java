package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Message;
import com.example.entity.MsgSession;
import com.example.entity.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MsgSessionMapper extends BaseMapper<MsgSession> {

    @Options(useGeneratedKeys = true, keyColumn = "ms_id", keyProperty = "ms_id")
    @Insert("INSERT INTO t_msgsession(openId,openId2) " +
            "VALUES (#{openId},#{openId2})")
    int add(MsgSession msgSession);

    @Select("SELECT * FROM t_msgsession where (openId = #{openId1}  AND openId2 = #{openId2}) or (openId = #{openId2}  AND openId2 = #{openId1}) ")
    MsgSession get(String openId1, String openId2);

//    @Select("select * from t_msgsession where openId=#{openId} or openId2=#{openId_copy}")
    @Select("select * from t_msgsession as m inner join t_wxuserinfo as u " +
            "on m.openId = u.openid or m.openId2 = u.openid " +
            "where (m.openId=#{openId} or m.openId2=#{openId_copy}) and u.openid!=#{openId_copy1}; ")
    List<Map<String,Object>> getAllMsgSession(String openId, String openId_copy, String openId_copy1);

}
