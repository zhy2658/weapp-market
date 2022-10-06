package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.PublishImg;
import com.example.entity.PublishLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PublishImgMapper  extends BaseMapper<PublishImg> {

    @Select("select * from t_publish_image where pid=#{pid} ")
    public List<Map<String,Object>> findByPId(@Param("pid") Integer pid);
}
