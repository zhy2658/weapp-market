package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.BigType;


/**
 * 商品大类Mapper接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 8:53
 */
public interface BigTypeMapper extends BaseMapper<BigType> {

    /**
     * 根据id查询商品大类
     * @param id
     * @return
     */
    public BigType findById(Integer id);

}
