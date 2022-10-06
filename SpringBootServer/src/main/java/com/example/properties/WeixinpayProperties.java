package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置文件
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties(prefix="weixinpayconfig")
@Data
public class WeixinpayProperties {

	private String appid; // 公众账号ID
	
	private String mch_id; // 商户号
	
	private String device_info; // 设备号
	
	private String key; // 商户的key【API密匙】
	
	private String url; // api请求地址
	
	private String notify_url; // 服务器异步通知页面路径
	
	private String return_url; // 服务器同步通知页面路径


}
