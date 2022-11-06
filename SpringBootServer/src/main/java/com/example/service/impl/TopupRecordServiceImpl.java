package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.TopupRecord;
import com.example.mapper.TopupRecordMapper;
import com.example.service.TopupRecordService;
import org.springframework.stereotype.Service;

@Service("TopupRecordService")
public class TopupRecordServiceImpl extends ServiceImpl<TopupRecordMapper,TopupRecord> implements TopupRecordService {
}
