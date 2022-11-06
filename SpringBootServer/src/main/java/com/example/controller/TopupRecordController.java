package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.properties.WeixinpayProperties;
import com.example.service.IWxUserInfoService;
import com.example.service.TopupRecordService;
import com.example.util.*;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/topupRecord")
public class TopupRecordController {


    @Resource
    private WeixinpayProperties weixinpayProperties;

    @Resource
    TopupRecordService topupRecordService;

    @Resource
    IWxUserInfoService iWxUserInfoService;


//    @PostMapping("/topup")
//    public R topup(HttpServletRequest request){
//
//
//        return R.ok();
//    }
    /**
     * 创建订单，返回订单号
     *
     * @return
     */
    @RequestMapping("/create")
    public R create(@RequestBody TopupRecord topupRecord, @RequestHeader(value = "token") String token) {
        System.out.println("order" + topupRecord);
        if (StringUtil.isNotEmpty(token)) {
            Claims claims = JwtUtils.validateJWT(token).getClaims();
            if (claims != null) {
                String openId = claims.getId();
                topupRecord.setOpenId(openId);
                topupRecord.setTopupNo("LYTOPUP" + DateUtil.getCurrentDateStr());
            } else {
                return R.error(500, "鉴权失败！");
            }
        } else {
            return R.error(500, "无权限访问！");
        }
        topupRecordService.save(topupRecord);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("topupNo", topupRecord.getTopupNo());
        return R.ok(resultMap);
    }

    /**
     * 预付款
     *
     * @param topuopNo
     * @param token
     * @return
     */
    @RequestMapping("/preparePay")
    public R preparePay(@RequestBody String topupNo, @RequestHeader(value = "token") String token) throws Exception {
        System.out.println("topuopNo:" + topupNo);
        System.out.println("token:" + token);
        String openId = null;
        if (StringUtil.isNotEmpty(token)) {
            Claims claims = JwtUtils.validateJWT(token).getClaims();
            if (claims != null) {
                openId = claims.getId();
                System.out.println("openId:" + openId);
            } else {
                return R.error(500, "鉴权失败！");
            }
        } else {
            return R.error(500, "无权限访问！");
        }

        TopupRecord order = topupRecordService.getOne(new QueryWrapper<TopupRecord>().eq("topupNo", topupNo));

        System.out.println("totalPrice:" + order.getTotalPrice().movePointRight(2));

        System.out.println("=======");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appid", weixinpayProperties.getAppid());
        map.put("mch_id", weixinpayProperties.getMch_id());
        map.put("openid", openId);
        map.put("nonce_str", StringUtil.getRandomString(32)); // 随机串
        map.put("device_info", weixinpayProperties.getDevice_info());
        map.put("notify_url", weixinpayProperties.getNotify_url());
        map.put("trade_type", "JSAPI"); // 交易类型
        map.put("out_trade_no", topupNo);
        map.put("body", "米粒点数充值");
        // map.put("total_fee", order.getTotalPrice().movePointRight(2));
//         map.put("total_fee", 1);  // 1分钱测试
        map.put("total_fee", order.getTotalPrice().movePointRight(2));
        // map.put("spbill_create_ip", getRemortIP(request)); // 终端IP
        map.put("spbill_create_ip", "127.0.0.1"); // 终端IP
        map.put("sign", getSign(map)); // 签名
        String xml = XmlUtil.genXml(map);
        System.out.println(xml);

        HttpResponse httpResponse = HttpClientUtil.sendXMLDataByPost(weixinpayProperties.getUrl().toString(), xml);
        String httpEntityContent = HttpClientUtil.getHttpEntityContent(httpResponse);
        System.out.println("httpEntityContent:" + httpEntityContent);

        Map resultMap = XmlUtil.doXMLParse(httpEntityContent);  // 解析返回的xml结果
        System.out.println("resultMap=" + resultMap);
        if (resultMap.get("result_code").equals("SUCCESS")) {
            // 封装返回值
            Map<String, Object> payMap = new HashMap<String, Object>();
            payMap.put("appId", resultMap.get("appid"));
            payMap.put("nonceStr", StringUtil.getRandomString(32));
            payMap.put("package", "prepay_id=" + resultMap.get("prepay_id"));
            payMap.put("timeStamp", System.currentTimeMillis() / 1000 + "");
            payMap.put("signType", "MD5");
            String paySign = getSign(payMap);
            System.out.println("paySign=" + paySign);
            System.out.println("==========");
            payMap.put("paySign", paySign); // 重新签名
            payMap.put("topupNo", topupNo);
            System.out.println("payMap=" + payMap);

            return R.ok(payMap);
        } else {
            return R.error(500, "系统报错，请联系管理员");
        }


    }

    //把订单设置为已支付状态
    @RequestMapping("/setOrderStatus")
    public R setOrderStatus(@RequestBody TopupRecord topupRecord){
        topupRecord=topupRecordService.getOne(
                new QueryWrapper<TopupRecord>()
                        .eq("topupNo",topupRecord.getTopupNo())
        );
        Map<String, Object> map = new HashMap<String, Object>();
        //设置订单状态
        topupRecord.setStatus(1);
        topupRecordService.updateById(topupRecord);
        WxUserInfo user = iWxUserInfoService.findByOpenId(topupRecord.getOpenId());
//        加米粒数量
        user.setCoin( topupRecord.getCoinNum() +user.getCoin());
        iWxUserInfoService.updateById(user);

        //发送短信通知
//        HttpClientUtil.getInstance().sendHttpGet("http://sms.hutonginfo.com:9000/sms.aspx?action=send&userid=8038&account=2658991831@qq.com&password=zhy2958991831&mobile="+tel+"&content=【晚点的心声】您有新的订单("+orderNo+")等待处理，请及时查看！&sendTime=&extno=");

//        System.out.println("httpEntityContent:" + httpEntityContent);

        return R.ok(map);
    }



    /**
     * 微信支付签名算法sign
     */
    private String getSign(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        String[] keyArr = (String[]) map.keySet().toArray(new String[map.keySet().size()]);//获取map中的key转为array
        Arrays.sort(keyArr);//对array排序
        for (int i = 0, size = keyArr.length; i < size; ++i) {
            if ("sign".equals(keyArr[i])) {
                continue;
            }
            sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        sb.append("key=" + weixinpayProperties.getKey());
        String sign = string2MD5(sb.toString());
        return sign;
    }

    /***
     * MD5加码 生成32位md5码
     */
    private String string2MD5(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }




}
