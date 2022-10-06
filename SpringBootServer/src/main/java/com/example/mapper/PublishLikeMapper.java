package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.PublishLike;
import com.example.entity.SmallType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PublishLikeMapper extends BaseMapper<PublishLike> {

    @Select("select l.id,l.pid,l.openId,l.ltime,u.nickName " +
            "from t_publish_like as l inner join t_wxuserinfo as u " +
            "on l.openId = u.openid " +
            "where l.pid=#{pid} order by l.ltime ")
    public List<Map<String,Object>> findByPId(@Param("pid") Integer pid);

}
