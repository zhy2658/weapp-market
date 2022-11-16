package com.example.controller;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.TopupRecord;
import com.example.entity.WxUserInfo;
import com.example.service.IOrderService;
import com.example.service.IWxUserInfoService;
import com.example.service.TopupRecordService;
import com.example.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.entity.Order;
import com.example.properties.WeixinpayProperties;

/**
 * 微信支付Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/weixinpay")
public class WeixinpayController {

	@Autowired
	private WeixinpayProperties weixinpayProperties;

	@Autowired
	private IOrderService orderService;

	@Resource
	private TopupRecordService topupRecordService;

	@Resource
	IWxUserInfoService iWxUserInfoService;

	private final static Logger logger= LoggerFactory.getLogger(WeixinpayController.class);


	
	/**
	 * 微信支付服务器异步通知
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/notifyUrl")
	public synchronized void notifyUrl(HttpServletRequest request)throws Exception{
		System.out.println("/notifyUrl");
		logger.info("notifyUrl");
		//读取参数    
        InputStream inputStream ;    
        StringBuffer sb = new StringBuffer();    
        inputStream = request.getInputStream();    
        String s ;    
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));    
        while ((s = in.readLine()) != null){    
            sb.append(s);    
        }    
        in.close();    
        inputStream.close();
		logger.info("sb:"+sb.toString());
        //解析xml成map    
        Map<String, String> m = new HashMap<String, String>();    
        m = XmlUtil.doXMLParse(sb.toString());    
            
        //过滤空 设置 TreeMap    
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();          
        Iterator<String> it = m.keySet().iterator();    
        while (it.hasNext()) {    
            String parameter = it.next();    
            String parameterValue = m.get(parameter);    
                
            String v = "";    
            if(null != parameterValue) {    
                v = parameterValue.trim();    
            } 
            logger.info("p:"+parameter+",v:"+v);
            packageParams.put(parameter, v);    
        }  
        
        // 微信支付的API密钥    
        String key = weixinpayProperties.getKey();
		String out_trade_no=(String) packageParams.get("out_trade_no");

		System.out.println("out_trade_no:"+out_trade_no);

		if (isTenpaySign("UTF-8", packageParams, key)) { // 验证通过
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				TopupRecord topupRecord = topupRecordService.getOne(new QueryWrapper<TopupRecord>().eq("topupNo", out_trade_no));
				System.out.println("topupRecord:"+topupRecord);
				if (topupRecord != null && topupRecord.getStatus() == 0) {
					topupRecord.setPayDate(new Date());
					System.out.println("设置支付状态");
					System.out.println(topupRecord.getTopupNo());
					topupRecord.setStatus(1);  // 设置支付状态已经支付
				    // orderService.save(order);
					topupRecordService.saveOrUpdate(topupRecord);

					// 加米粒数量
					WxUserInfo user = iWxUserInfoService.findByOpenId(topupRecord.getOpenId());
					user.setCoin( topupRecord.getCoinNum() +user.getCoin());
					iWxUserInfoService.updateById(user);

					System.out.println("代码结束");
//					logger.info(out_trade_no + "：微信服务器异步修改订单状态成功！");

				}
			}
		} else {
//			logger.info(out_trade_no + "：微信服务器异步验证失败！");
		}


	}



	/**
	 * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @return boolean
	 */
	public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + API_KEY);

		//算出摘要
		String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();

		return tenpaySign.equals(mysign);
	}

}
