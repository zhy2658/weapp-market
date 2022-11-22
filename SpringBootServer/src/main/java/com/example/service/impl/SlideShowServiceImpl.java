package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SlideShow;
import com.example.entity.TopupRecord;
import com.example.mapper.SlideShowMapper;
import com.example.mapper.TopupRecordMapper;
import com.example.service.SlideShowService;
import com.example.service.TopupRecordService;
import org.springframework.stereotype.Service;

@Service("SlideShowService")
public class SlideShowServiceImpl extends ServiceImpl<SlideShowMapper, SlideShow> implements SlideShowService {
}
