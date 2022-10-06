package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.WxUserInfo;
import org.apache.ibatis.annotations.Update;

/**
 * 微信用户Mapper接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 8:53
 */
public interface WxUserInfoMapper extends BaseMapper<WxUserInfo> {

    /**
     * 根据openId查询用户信息
     * @param openid
     * @return
     */
    public WxUserInfo findByOpenId(String openid);

    @Update("update t_wxuserinfo set isshow = #{isshow} where openid=#{openId}")
    public void updateUserShow(String openId,int isshow);

    public Integer update(WxUserInfo wxUserInfo);

}
