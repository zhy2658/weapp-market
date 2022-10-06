package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.SmallType;

import java.util.List;
import java.util.Map;


/**
 * 商品小类Mapper接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 8:53
 */
public interface SmallTypeMapper extends BaseMapper<SmallType> {

    /**
     * 根据id查询商品小类
     * @param id
     * @return
     */
    public SmallType findById(Integer id);

    /**
     * 根据条件 分页查询商品小类
     * @param map
     * @return
     */
    public List<SmallType> list(Map<String,Object> map);

    /**
     * 根据条件，查询商品小类总记录数
     * @param map
     * @return
     */
    public Long getTotal(Map<String,Object> map);

    /**
     * 添加商品小类
     * @param smallType
     * @return
     */
    public Integer add(SmallType smallType);

    /**
     * 修改商品小类
     * @param smallType
     * @return
     */
    public Integer update(SmallType smallType);

}
