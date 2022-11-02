package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.PublishReply;
import com.example.entity.SmallType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PublishReplyMapper extends BaseMapper<PublishReply> {

    @Select("select p.id,p.pid,p.openId,p.msg,p.retime,u.nickName,u.avatarUrl " +
            "from t_publish_reply as p inner join t_wxuserinfo as u " +
            "on p.openId = u.openid " +
            "where p.pid=#{pid} order by p.retime ")
    public List<Map<String,Object>> findByPId(@Param("pid") Integer pid);
}
