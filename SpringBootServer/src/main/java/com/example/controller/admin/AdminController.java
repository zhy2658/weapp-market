package com.example.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.constant.SystemConstant;
import com.example.entity.Admin;
import com.example.entity.R;
import com.example.entity.WxUserInfo;
import com.example.service.IAdminService;
import com.example.service.IWxUserInfoService;
import com.example.util.JwtUtils;
import com.example.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员Controller
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-05 7:42
 */
@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Resource
    private IWxUserInfoService wxUserInfoService;

    private final static Logger logger= LoggerFactory.getLogger(AdminController.class);

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    @PostMapping("/adminLogin")
    public R adminLogin(HttpServletRequest request, @RequestBody Admin admin){
        if(admin==null){
            return R.error();
        }
        if(StringUtil.isEmpty(admin.getUserName())){
            return R.error("用户名不能为空！");
        }
        if(StringUtil.isEmpty(admin.getPassword())){
            return R.error("密码不能为空！");
        }
        WxUserInfo wxUserInfo = wxUserInfoService.getOne(new QueryWrapper<WxUserInfo>().eq("employee_id", admin.getUserName()));
//        是员工情况
        if (wxUserInfo != null){
            Map<String,Object> resultMap=new HashMap<String,Object>();
            String openId= wxUserInfo.getOpenid();
            if( wxUserInfo.getPassword()==null){
                request.getSession().setAttribute("openId", openId);
                String token = JwtUtils.createJWT(openId, wxUserInfo.getNickName(), SystemConstant.JWT_TTL);
                resultMap.put("power","employee");
                resultMap.put("isSetNewPwd",true);
                resultMap.put("token", token);
                resultMap.put("userInfo", wxUserInfo);
                return R.ok(resultMap);
            }
            else if(wxUserInfo.getPassword().equals(admin.getPassword())){
                request.getSession().setAttribute("openId", openId);
                String token = JwtUtils.createJWT(openId, wxUserInfo.getNickName(), SystemConstant.JWT_TTL);
                resultMap.put("power","employee");
                resultMap.put("isSetNewPwd",false);
                resultMap.put("token", token);
                resultMap.put("userInfo", wxUserInfo);
                return R.ok(resultMap);
            }

            return R.error("用户名或者密码错误！");

        }

//        系统管理员情况
        Admin resultAdmin = adminService.getOne(new QueryWrapper<Admin>().eq("userName", admin.getUserName()));
        if(resultAdmin==null){
            return R.error("用户名或者密码错误！");
        }
        if(!resultAdmin.getPassword().trim().equals(admin.getPassword())){
            return R.error("用户名或者密码错误！");
        }
        String token = JwtUtils.createJWT("-1", "admin", SystemConstant.JWT_TTL);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("token",token);
        return R.ok(resultMap);
    }

    /**
     * 修改密码
     * @param admin
     * @return
     */
    @PostMapping("/admin/modifyPassword")
    public R modifyPassword(@RequestBody Admin admin){
        if(StringUtil.isEmpty(admin.getUserName())){
            return R.error("用户名不能为空！");
        }
        if(StringUtil.isEmpty(admin.getNewPassword())){
            return R.error("新密码不能为空！");
        }
        adminService.update(admin);
        return R.ok();
    }

}
