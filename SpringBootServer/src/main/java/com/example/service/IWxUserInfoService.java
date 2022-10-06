package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.WxUserInfo;


/**
 * 微信用户Service接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:03
 */
public interface IWxUserInfoService extends IService<WxUserInfo> {

    WxUserInfo findByOpenId(String openId);

    void updateUserShow(String openId,int isshow);

    Integer update(WxUserInfo wxUserInfo);

}
