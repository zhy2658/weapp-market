package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Admin;
import com.example.entity.ExtraPayitem;
import com.example.mapper.AdminMapper;
import com.example.mapper.ExtraPayitemMapper;
import com.example.service.ExtraPayitemService;
import com.example.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service("ExtraPayitemService")
public class ExtraPayitemServiceImpl extends ServiceImpl<ExtraPayitemMapper, ExtraPayitem> implements ExtraPayitemService {



}
