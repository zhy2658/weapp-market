package com.example.service;

import com.example.entity.PageBean;

import java.util.List;
import java.util.Map;

public interface StatisticsService {


    List<Map<String,Object>> getOrderDesc(PageBean pageBean);
}
