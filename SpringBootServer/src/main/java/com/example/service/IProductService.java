package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * 商品Service接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:03
 */
public interface IProductService extends IService<Product> {

    /**
     * 查询轮播商品
     * @return
     */
    public List<Product> findSwiper();

    /**
     * 查询热卖商品
     * @return
     */
    public List<Map<String,Object>> findHot();

    /**
     * 根据条件 分页商品
     * @param map
     * @return
     */
    public List<Map<String,Object>> list(Map<String,Object> map);

    /**
     * 根据条件，查询商品总记录数
     * @param map
     * @return
     */
    public Long getTotal(Map<String,Object> map);

    /**
     * 添加商品
     * @param product
     * @return
     */
    public Integer add(Product product);

    /**
     * 修改商品
     * @param product
     * @return
     */
    public Integer update(Product product);

}
