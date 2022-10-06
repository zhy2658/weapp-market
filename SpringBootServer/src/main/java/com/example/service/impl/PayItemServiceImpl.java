package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.PayItem;
import com.example.entity.SmallType;
import com.example.mapper.PayItemMapper;
import com.example.mapper.SmallTypeMapper;
import com.example.service.ISmallTypeService;
import com.example.service.PayItemService;
import org.springframework.stereotype.Service;

@Service("PayItemService")
public class PayItemServiceImpl extends ServiceImpl<PayItemMapper, PayItem> implements PayItemService {

}
