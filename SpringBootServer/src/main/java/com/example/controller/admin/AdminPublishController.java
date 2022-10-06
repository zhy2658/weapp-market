package com.example.controller.admin;


import com.example.entity.PageBean;
import com.example.entity.R;
import com.example.service.PublishService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/publish")
public class AdminPublishController {

    @Resource
    PublishService publishService;

    @RequestMapping("/get_allpublish")
    public R getMyPublish(HttpServletRequest request,
                          @RequestBody PageBean pageBean,
                          @RequestParam("status") int status) {

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("status", status);
        pageMap.put("start", pageBean.getStart());
        pageMap.put("pageSize", pageBean.getPageSize());


        List<Map<String, Object>> publishs = publishService.getAllPublishByStatus(pageMap);
        int total = publishService.getTotalByStatus(status);
        Map map = new HashMap<String, Object>();
        map.put("publishs", publishs);
        map.put("total", total);
        return R.ok(map);

    }

    @RequestMapping("/update_publish_status")
    public Map<String, Object> updateStatusByPid(@Param("pid") int pid, @Param("status") int status) {
        publishService.updateStatusByPid(pid, status);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "更新成功");
        return R.ok(resultMap);

    }


}
