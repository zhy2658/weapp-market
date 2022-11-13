package com.example.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.service.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/admin/statistics")
public class AdminStatisticsController {


    @Resource
    private IOrderService orderService;

    @Resource
    private IOrderDetailService orderDetailService;

    @Resource
    IWxUserInfoService iWxUserInfoService;

    @Resource
    StatisticsService statisticsService;

    @Resource
    PublishService publishService;

    /**
     * 根据条件分页查询
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(HttpServletRequest request, @RequestBody PageBean pageBean){
        Integer pageSize= pageBean.getPageSize();
        Integer pageNum= pageBean.getPageNum();
        String begin= pageBean.getBegin();
        String end= pageBean.getEnd();

//       按时间导出
        // 员工号 昵称  性别   手机号   员工等级  帖子发布数  随机订单数量  指定订单数量  拒接订单数 总接单量 投诉次数  订单总受益
        Page<WxUserInfo> page=new Page<>(pageBean.getPageNum(),pageBean.getPageSize());
        Page<WxUserInfo> pageResult = iWxUserInfoService.page(page,
                new QueryWrapper<WxUserInfo>()
                        .eq("admin",1)
                        .orderByDesc("lastLoginDate")
        );
        int total = iWxUserInfoService.count(
                new QueryWrapper<WxUserInfo>()
                        .eq("admin",1)
        );
        List<WxUserInfo> tempUserList = pageResult.getRecords();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(WxUserInfo tempUser : tempUserList){
            Map<String,Object> map =new HashMap<>();
            map.put("employee_id",tempUser.getEmployee_id());
            map.put("nickName",tempUser.getNickName());
            map.put("sex",tempUser.getSex());
            map.put("employee_grade",tempUser.getEmployee_grade());
            map.put("tel",tempUser.getTel());
            map.put("admin",tempUser.getAdmin());
//            帖子发布数
            int publishCount = publishService.count(
                    new QueryWrapper<Publish>()
                            .eq("status",1)
                            .eq("openId",tempUser.getOpenid())
                            .gt("pubtime",begin)
                            .lt("pubtime",end)
            );
//            随机订单数量
            int randomOrderCount = orderService.count(
                    new QueryWrapper<Order>()
                            .eq("type",1)
                            .eq("servant_id",tempUser.getOpenid())
                            .eq("status",4)
                            .gt("finishDate",begin)
                            .lt("finishDate",end)
            );
//            指定订单数量  拒接订单数 总接单量 投诉次数  订单总受益
            int assignOrderCount = orderService.count(
                    new QueryWrapper<Order>()
                            .eq("type",0)
                            .eq("servant_id",tempUser.getOpenid())
                            .eq("status",4)
                            .gt("finishDate",begin)
                            .lt("finishDate",end)
            );
            // 拒接订单数
            int refuseOrderCount = orderService.count(
                    new QueryWrapper<Order>()
                            .eq("status",9)
                            .eq("servant_id",tempUser.getOpenid())
                            .gt("createDate",begin)
                            .lt("createDate",end)
            );
            //         总受益
            List<Order> revenueOrder = orderService.list(
                    new QueryWrapper<Order>()
                            .eq("status",4)
                            .eq("servant_id",tempUser.getOpenid())
                            .gt("finishDate",begin)
                            .lt("finishDate",end)
            );
            Float totalRevenue= 0f;
            for(Order order: revenueOrder){
                totalRevenue += order.getTotalPrice().floatValue();
            }

//            总接单量
            int totalCount = assignOrderCount+randomOrderCount;
//            投诉次数  订
            int conpainCount =0;
//

            map.put("totalRevenue",totalRevenue);
            map.put("publishCount",publishCount);
            map.put("randomOrderCount",randomOrderCount);
            map.put("assignOrderCount",assignOrderCount);
            map.put("refuseOrderCount",refuseOrderCount);
            map.put("totalCount",totalCount);
            map.put("conpainCount",conpainCount);
            mapList.add(map);

        }
//        List<Map<String,Object>> mapList = new ArrayList<>();
//        mapList.stream().sorted(Comparator.comparing(( Map<String,Object> map)->(  (Float) map.get("totalRevenue"))  ).reversed() );

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("orderList",mapList);
        resultMap.put("total",total);
        return R.ok(resultMap);
    }

}
