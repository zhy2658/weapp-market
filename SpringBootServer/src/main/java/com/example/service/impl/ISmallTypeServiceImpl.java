package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SmallType;
import com.example.mapper.SmallTypeMapper;
import com.example.service.ISmallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 商品小类Service实现类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:08
 */
@Service("smallTypeService")
public class ISmallTypeServiceImpl extends ServiceImpl<SmallTypeMapper,SmallType> implements ISmallTypeService {

    @Resource
    private SmallTypeMapper SmallTypeMapper;


    @Override
    public List<SmallType> list(Map<String, Object> map) {
        return SmallTypeMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return SmallTypeMapper.getTotal(map);
    }

    @Override
    public Integer add(SmallType smallType) {
        return SmallTypeMapper.add(smallType);
    }

    @Override
    public Integer update(SmallType smallType) {
        return SmallTypeMapper.update(smallType);
    }
}
