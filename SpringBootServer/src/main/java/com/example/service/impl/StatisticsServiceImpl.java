package com.example.service.impl;

import com.example.entity.PageBean;
import com.example.mapper.StatisticsMapper;
import com.example.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("StatisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    StatisticsMapper statisticsMapper;

    @Override
    public List<Map<String, Object>> getOrderDesc(PageBean pageBean) {
        return statisticsMapper.getOrderDesc(pageBean);

    }
}
