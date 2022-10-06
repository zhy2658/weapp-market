package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.service.IBigTypeService;
import com.example.service.IProductService;
import com.example.service.ISmallTypeService;
import com.example.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品大类控制器
 *
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:16
 */
@RestController
@RequestMapping("/bigType")
public class BigTypeController {

    @Autowired
    private IBigTypeService bigTypeService;

    @Autowired
    private ISmallTypeService smallTypeService;

    @Autowired
    private IProductService productService;

    @Resource
    private IWxUserInfoService iWxUserInfoService;

    /**
     * 查询所有商品大类
     *
     * @return
     */
    @RequestMapping("/findAll")
    public R findAll() {
        List<BigType> bigTypeList = bigTypeService.list(new QueryWrapper<BigType>().orderByAsc("id"));
        Map<String, Object> map = new HashMap<>();
        map.put("message", bigTypeList);
        return R.ok(map);
    }

    /**
     * 获取所有菜单信息
     *
     * @return
     */
    @RequestMapping("/findCategories")
    public R findCategories() {
        List<BigType> bigTypeList = bigTypeService.list(new QueryWrapper<BigType>().orderByAsc("id"));
        for (BigType bigType : bigTypeList) {
            List<SmallType> smallTypeList = smallTypeService.list(new QueryWrapper<SmallType>().eq("bigTypeId", bigType.getId()));
            bigType.setSmallTypeList(smallTypeList);
            for (SmallType smallType : smallTypeList) {
                QueryWrapper<WxUserInfo> queryWrapper = new QueryWrapper<WxUserInfo>();
                List<WxUserInfo> userInfos = iWxUserInfoService.list(
                        queryWrapper
                                .eq("small_id", smallType.getId())
                                .eq("isshow", 1)
                ); //.eq("isshow", 1)
                smallType.setUserInfos(userInfos);
            }

        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", bigTypeList);
        return R.ok(map);
    }


}
