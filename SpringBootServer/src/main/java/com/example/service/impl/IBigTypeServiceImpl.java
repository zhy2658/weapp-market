package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.BigType;
import com.example.mapper.BigTypeMapper;
import com.example.service.IBigTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 商品大类Service实现类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:08
 */
@Service("bigTypeService")
public class IBigTypeServiceImpl extends ServiceImpl<BigTypeMapper,BigType> implements IBigTypeService {

    @Resource
    private BigTypeMapper bigTypeMapper;


}
