package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Admin;

/**
 * 管理员Mapper接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 8:53
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 修改
     * @param admin
     * @return
     */
    public Integer update(Admin admin);

}
