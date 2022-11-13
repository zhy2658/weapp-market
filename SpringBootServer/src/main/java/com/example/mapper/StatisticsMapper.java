package com.example.mapper;


import com.example.entity.PageBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {



     List<Map<String, Object>> getOrderDesc(PageBean pageBean);
}
