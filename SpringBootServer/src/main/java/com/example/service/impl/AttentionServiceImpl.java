package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Attention;
import com.example.entity.ExtraPayitem;
import com.example.mapper.AttentionMapper;
import com.example.mapper.ExtraPayitemMapper;
import com.example.service.AttentionService;
import com.example.service.ExtraPayitemService;
import org.springframework.stereotype.Service;

@Service("AttentionService")
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention> implements AttentionService {


}
