package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.PageBean;
import com.example.entity.Publish;
import com.example.entity.PublishImg;
import com.example.mapper.PublishMapper;
import com.example.service.PublishService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("PublishService")
public class PublishServiceImpl extends ServiceImpl<PublishMapper,Publish> implements PublishService {

    @Resource
    PublishMapper publishMapper;

    @Override
    public void add(Publish publish, List<PublishImg> publishImgs) {
        publishMapper.addPulish(publish);
        for(PublishImg pulishImg : publishImgs){
            pulishImg.setPid(publish.getPid());
        }
        if(publishImgs.size() > 0)publishMapper.addPublishImg(publishImgs);


    }

    @Override
    public List<Map<String,Object>> getMyPublish(PageBean pageBean) {
        return publishMapper.getMyPublish(pageBean);
    }

    @Override
    public List<Map<String, Object>> getAllPublishByStatus(Map<String,Object>pageMap) {
        return publishMapper.getAllPublishByStatus(pageMap);
    }
    public int getTotalByStatus(int status){
        return publishMapper.getTotalByStatus(status);
    }

    @Override
    public void updateStatusByPid(int pid, int status) {
        publishMapper.updateStatusByPid(pid,status);
    }

    @Override
    public List<Map<String,Object>> getRandom(PageBean pageBean) {

        return publishMapper.getRandom(pageBean);
    }

    @Override
    public Publish selectOneByPid(int pid) {
        return publishMapper.selectOneByPid(pid);
    }
}
