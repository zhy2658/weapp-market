package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.PageBean;
import com.example.entity.PayItem;
import com.example.entity.Publish;
import com.example.entity.PublishImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PublishService extends IService<Publish> {

    void add(Publish pulish, List<PublishImg> pulishImgs);

    List<Map<String, Object>> getMyPublish(PageBean pageBean);

    List<Map<String, Object>> getAllPublishByStatus(Map<String, Object> pageMap);

    int getTotalByStatus(int status);

    void updateStatusByPid(int pid, int status);

    List<Map<String, Object>> getRandom(PageBean pageBean);

    Publish selectOneByPid(int pid);
}
