package com.example.service;

import com.example.entity.PageBean;
import com.example.entity.Publish;
import com.example.entity.PublishImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PublishService {

    void add(Publish pulish, List<PublishImg> pulishImgs);

    List<Map<String, Object>> getMyPublish(String openId);

    List<Map<String, Object>> getAllPublishByStatus(Map<String, Object> pageMap);

    int getTotalByStatus(int status);

    void updateStatusByPid(int pid, int status);

    List<Map<String, Object>> getRandom(PageBean pageBean);

    Publish selectOneByPid(int pid);
}
