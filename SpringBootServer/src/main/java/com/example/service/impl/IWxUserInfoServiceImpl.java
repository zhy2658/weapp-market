package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.WxUserInfo;
import com.example.mapper.WxUserInfoMapper;
import com.example.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 微信用户信息Service实现类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:08
 */
@Service("wxUserInfoService")
public class IWxUserInfoServiceImpl extends ServiceImpl<WxUserInfoMapper,WxUserInfo> implements IWxUserInfoService {

    @Resource
    private WxUserInfoMapper wxUserInfoMapper;


    @Override
    public WxUserInfo findByOpenId(String openId) {
        return wxUserInfoMapper.findByOpenId(openId);
    }

    @Override
    public void updateUserShow(String openId, int isshow) {
        wxUserInfoMapper.updateUserShow(openId,isshow);
    }

    @Override
    public Integer update(WxUserInfo wxUserInfo) {

        return wxUserInfoMapper.update(wxUserInfo);
    }
}
