package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Product;
import com.example.mapper.ProductMapper;
import com.example.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品Service实现类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:08
 */
@Service("productService")
public class IProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> findSwiper() {
        return productMapper.findSwiper();
    }

    @Override
    public List<Map<String,Object>> findHot() {
        return productMapper.findHot();
    }

    @Override
    public List<Map<String,Object>> list(Map<String, Object> map) {
        List<Map<String,Object>> list= productMapper.list(map);
        return list;
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return productMapper.getTotal(map);
    }

    @Override
    public Integer add(Product product) {
        return productMapper.add(product);
    }

    @Override
    public Integer update(Product product) {
        return productMapper.update(product);
    }
}
