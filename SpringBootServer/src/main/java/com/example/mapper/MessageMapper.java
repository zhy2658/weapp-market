package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Message;
import com.example.entity.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 根据id查询商品大类
     * @param message
     * @return
     */
    @Insert("INSERT INTO t_message(sendOpenID,receiverOpenId,msg,`time`,ms_id) " +
            "VALUES (#{sendOpenID},#{receiverOpenId},#{msg},#{time},#{ms_id});")
    public Integer addMessage(Message message);

    @Select("select * from t_message where sendOpenID = #{myOpenId} and receiverOpenId = #{oppositeOpenId} order by `time` desc limit 0,10;")
    public List<Message> getSenderMessage(Map<String,Object> map);
    @Select("select * from t_message where sendOpenID = #{oppositeOpenId} and receiverOpenId = #{myOpenId} order by `time` desc limit 0,10; ")
    public List<Message> getReceiverMessage(Map<String,Object> map);
}
