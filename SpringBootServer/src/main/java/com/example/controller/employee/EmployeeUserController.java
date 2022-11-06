package com.example.controller.employee;


import com.example.entity.Admin;
import com.example.entity.Publish;
import com.example.entity.R;
import com.example.entity.WxUserInfo;
import com.example.service.IWxUserInfoService;
import com.example.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employee/user")
public class EmployeeUserController {

    @Resource
    private IWxUserInfoService wxUserInfoService;


    @RequestMapping("/getUserInfo")
    public Map<String,Object> selectOne(HttpServletRequest request){
       String openId=(String) request.getSession().getAttribute("openId");
       WxUserInfo userInfo =wxUserInfoService.findByOpenId(openId);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("userInfo",userInfo);
        return resultMap;

    }
    @PostMapping("/modifyPassword")
    public R modifyPassword(@RequestBody WxUserInfo wxUserInfo){
        if(StringUtil.isEmpty(wxUserInfo.getEmployee_id().toString())){
            return R.error("用户名不能为空！");
        }
        if(StringUtil.isEmpty(wxUserInfo.getNewPassword())){
            return R.error("新密码不能为空！");
        }

        wxUserInfo.setPassword(wxUserInfo.getNewPassword());
        wxUserInfoService.update(wxUserInfo);
        return R.ok();
    }
}
