package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.PageBean;
import com.example.entity.Publish;
import com.example.entity.PublishImg;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PublishMapper extends BaseMapper<Publish> {

    @Insert("insert into t_publish(title,content,address,isimg,openId) " +
            "values(#{title},#{content},#{address},#{isimg},#{openId}) ")
    @Options(useGeneratedKeys = true, keyColumn = "pid", keyProperty = "pid")
    Integer addPulish(Publish pulish);


    @Insert({
            "<script>",
            "insert into t_publish_image(src,sort,pid) values ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.src}, #{item.sort}, #{item.pid})",
            "</foreach>",
            "</script>"
    })
    void addPublishImg(@Param(value = "list") List<PublishImg> publishImgs);


//    @Select("select * from t_publish where openId = #{openId} order by pubtime desc")
    List<Map<String,Object>> getMyPublish(PageBean pageBean);

//    @Select("select * from t_publish where status = #{status}")
    List<Map<String,Object>> getAllPublishByStatus(Map<String,Object>pageMap);

    int getTotalByStatus(@Param("status") int status);

    @Update("update t_publish set status= #{status}  where pid = #{pid}")
    void updateStatusByPid(int pid,int status);

//    @Select("select * from t_publish as p inner join t_wxuserinfo as u " +
//            "on p.openId = u.openid " +
//            "where p.status=1 " +
//            "ORDER BY RAND() LIMIT 0,6")
    List<Map<String,Object>> getRandom(PageBean pageBean);

    Publish selectOneByPid(int pid);

}
