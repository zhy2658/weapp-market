package com.example.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/playitem")
public class AdminPlayItemController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductSwiperImageService productSwiperImageService;

    @Resource
    private IWxUserInfoService iWxUserInfoService;

    @Resource
    private PayItemService payItemService;

    @Resource
    ExtraPayitemService extraPayitemService;

    @RequestMapping("/getByGrade")
    public R getByGrade(@RequestParam("grade") Integer grade){

        List<PayItem> payitemList = payItemService.list(new QueryWrapper<PayItem>().eq("grade",grade));
        Map<String,Object> map=new HashMap<>();
        map.put("payitemList",payitemList);
        return R.ok(map);
    }
    @PostMapping("/save")
    public R save(@RequestBody PayItem payItem){
        if(payItem.getId()==null || payItem.getId()==-1){
            payItemService.save(payItem);
        }else{
            payItemService.saveOrUpdate(payItem);
        }
        return R.ok();
    }

    @RequestMapping("/{id}")
    public R findById(@PathVariable(value = "id") Integer id){
        PayItem payItem = payItemService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("payitem",payItem);
        return R.ok(map);
    }
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        // 删除订单细表的数据
        extraPayitemService.remove(
                new QueryWrapper<ExtraPayitem>()
                        .eq("payitem_id",id)
        );
        payItemService.removeById(id);

        return R.ok();
    }
}
