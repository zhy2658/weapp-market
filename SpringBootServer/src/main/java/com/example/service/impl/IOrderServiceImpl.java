package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Order;
import com.example.mapper.OrderMapper;
import com.example.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 订单主表Service实现类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:08
 */
@Service("orderService")
public class IOrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements IOrderService {

    @Resource
    private OrderMapper orderMapper;


    @Override
    public List<Order> list(Map<String, Object> map) {
        return orderMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return orderMapper.getTotal(map);
    }
}
