package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.service.IProductService;
import com.example.service.IProductSwiperImageService;
import com.example.service.IWxUserInfoService;
import com.example.service.PayItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:16
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductSwiperImageService productSwiperImageService;

    @Resource
    private IWxUserInfoService iWxUserInfoService;

    @Resource
    private PayItemService payItemService;

    /**
     * 查询轮播商品
     * @return
     */
    @RequestMapping("/findSwiper")
    public R findSwiper(){
        List<Product> swiperList = productService.findSwiper();
        Map<String,Object> map=new HashMap<>();
        map.put("message",swiperList);
        return R.ok(map);
    }

    /**
     * 查询热门推荐商品
     * @return
     */
    @RequestMapping("/findHot")
    public R findHot(){
        List<Map<String,Object>> productList = productService.findHot();
        Map<String,Object> map=new HashMap<>();
        map.put("users",productList);
        return R.ok(map);
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public R detail(Integer id){
        Product product = productService.getById(id);
        WxUserInfo userInfo = iWxUserInfoService.findByOpenId(product.getOpenId());
        List<PayItem> payitemList = payItemService.list(new QueryWrapper<PayItem>().eq("openId",userInfo.getOpenid()));
        List<ProductSwiperImage> productSwiperImageList = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>().eq("productId", product.getId()).orderByAsc("sort"));
        product.setProductSwiperImageList(productSwiperImageList);
        Map<String,Object> map=new HashMap<>();
        map.put("payitemList",payitemList);
        map.put("message",product);
        map.put("userInfo",userInfo);
        return R.ok(map);
    }

    @RequestMapping("/detailByopenId")
    public R detailByopenId(@RequestParam("openid") String openId){
        //构建一个查询的wrapper
        QueryWrapper<Product> wrapper = new QueryWrapper<Product>();
        wrapper.eq("openId",openId);
        Product  product=productService.getOne(wrapper);

        WxUserInfo userInfo = iWxUserInfoService.findByOpenId(product.getOpenId());
        List<ProductSwiperImage> productSwiperImageList = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>().eq("productId", product.getId()).orderByAsc("sort"));
        product.setProductSwiperImageList(productSwiperImageList);
        Map<String,Object> map=new HashMap<>();
        map.put("message",product);
        map.put("userInfo",userInfo);
        return R.ok(map);
    }

    /**
     * 商品搜索
     * @param q
     * @return
     */
    @GetMapping("/search")
    public R search(String q){
        List<Product> producetList = productService.list(new QueryWrapper<Product>().like("name", q));
        Map<String,Object> map=new HashMap<>();
        map.put("message",producetList);
        return R.ok(map);
    }



}
