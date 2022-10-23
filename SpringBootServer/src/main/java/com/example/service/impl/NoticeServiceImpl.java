package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Notice;
import com.example.entity.PayItem;
import com.example.mapper.NoticeMapper;
import com.example.mapper.PayItemMapper;
import com.example.service.NoticeService;
import com.example.service.PayItemService;
import org.springframework.stereotype.Service;

@Service("NoticeMapper")
public class NoticeServiceImpl  extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}
