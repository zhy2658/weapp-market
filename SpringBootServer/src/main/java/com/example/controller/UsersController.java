package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.constant.SystemConstant;
import com.example.entity.Product;
import com.example.entity.R;
import com.example.entity.WxUserInfo;
import com.example.properties.WeixinProperties;
import com.example.service.IProductService;
import com.example.service.IWxUserInfoService;
import com.example.util.DateUtil;
import com.example.util.HttpClientUtil;
import com.example.util.JwtUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信用户Controller
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-01-08 15:50
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private WeixinProperties weixinProperties;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private IWxUserInfoService wxUserInfoService;

    @Resource
    IProductService iProductService;

    @Value("${productImagesFilePath}")
    private String productImagesFilePath;

    @Value("${AudioFilePath}")
    private String AudioFilePath;



    /**
     * 微信登录
     * @return
     */
    @RequestMapping("/wxlogin")
    public R wxLogin(@RequestBody WxUserInfo wxUserInfo){
//        System.out.println(weixinProperties);
//        System.out.println("code="+wxUserInfo.getCode());
        String jscode2sessionUrl=weixinProperties.getJscode2sessionUrl()+"?appid="+weixinProperties.getAppid()+"&secret="+weixinProperties.getSecret()+"&js_code="+wxUserInfo.getCode()+"&grant_type=authorization_code";
//        System.out.println(jscode2sessionUrl);
        String result = httpClientUtil.sendHttpGet(jscode2sessionUrl); // 带code请求获取openId
//        System.out.println(result);
        JSONObject jsonObject = JSON.parseObject(result);
        String openid = jsonObject.get("openid").toString(); // 获取openId
        WxUserInfo resultUserInfo = wxUserInfoService.getOne(new QueryWrapper<WxUserInfo>().eq("openid", openid));

        if(resultUserInfo==null){ // 不存在 插入用户
            wxUserInfo.setOpenid(openid);
            wxUserInfo.setRegisterDate(new Date());
            wxUserInfo.setLastLoginDate(new Date());
            wxUserInfoService.save(wxUserInfo);
            Product product = new Product();
            product.setOpenId(openid);
            iProductService.save(product);
            System.out.println(wxUserInfo.getId());
            resultUserInfo = wxUserInfo;
        }else{  // 存在 更新用户信息
            System.out.println("存在");
//            resultUserInfo.setNickName(wxUserInfo.getNickName());
//            resultUserInfo.setAvatarUrl(wxUserInfo.getAvatarUrl());
            resultUserInfo.setLastLoginDate(new Date());
            wxUserInfoService.updateById(resultUserInfo);
            wxUserInfo.setId(resultUserInfo.getId());
        }

        //把token返回给客户端
        String token = JwtUtils.createJWT(openid, wxUserInfo.getNickName(), SystemConstant.JWT_TTL);
//        System.out.println("token:"+token);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("token",token);
        resultMap.put("userInfo",resultUserInfo);
        return R.ok(resultMap);

    }

    @PostMapping ("/update_user")
    public R updateUser(@RequestBody WxUserInfo wxUserInfo){
        wxUserInfoService.update(wxUserInfo);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 0);
        map.put("msg", "更新成功！");
        return R.ok(map);
    }

    @RequestMapping("/uploadAvatarUrl")
    public Map<String,Object> uploadAvatarUrl(HttpServletRequest request, MultipartFile file) throws IOException {
        Map<String,Object> map=new HashMap<String,Object>();
        if(!file.isEmpty()){
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(productImagesFilePath+newFileName));

            String openId= (String)request.getSession().getAttribute("openId");
            WxUserInfo wxUserInfo =new WxUserInfo();
            wxUserInfo.setOpenid(openId);
            wxUserInfo.setAvatarUrl("/image/product/"+newFileName);
            wxUserInfoService.update(wxUserInfo);

            System.out.println(wxUserInfo);

            map.put("code", 0);
            map.put("msg", "上传成功");
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("title", newFileName);
            map2.put("avatarUrl", wxUserInfo.getAvatarUrl());
            map.put("data", map2);
        }
        return map;
    }

//    上传录音
    @RequestMapping("/uploadRecording")
    public Map<String,Object> uploadRecording(HttpServletRequest request,
                                              MultipartFile file,
                                              @RequestParam("audioTime") Integer audioTime) throws IOException {
        System.out.println("audioTime"+audioTime);
        Map<String,Object> map=new HashMap<String,Object>();
        if(!file.isEmpty()){
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(AudioFilePath+newFileName));

            String openId= (String)request.getSession().getAttribute("openId");
            Product product =new Product();
//            product.setId(-1);
            product.setOpenId(openId);
            product.setAudio("/uploads/audios/"+newFileName);
            product.setAudioTime(audioTime);
            System.out.println(product);
            iProductService.update(product);



            map.put("code", 0);
            map.put("msg", "上传成功");
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("title", newFileName);
            map2.put("audio", product.getAudio());
            map2.put("audioTime", product.getAudioTime());
            map.put("data", map2);
        }
        return map;
    }

}
