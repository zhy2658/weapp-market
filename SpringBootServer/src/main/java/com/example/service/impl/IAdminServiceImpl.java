package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Admin;
import com.example.mapper.AdminMapper;
import com.example.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 管理员Service实现类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:08
 */
@Service("adminService")
public class IAdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;


    @Override
    public Integer update(Admin admin) {
        return adminMapper.update(admin);
    }
}
