package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    ExtraPayitemService extraPayitemService;

    @Resource
    AttentionService attentionService;

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
     * 根据id查询用户
     * @param openId
     * @return
     */

    @RequestMapping("/getUser/{openId}")
    public R detail(HttpServletRequest request,@PathVariable(value = "openId") String openId){
        WxUserInfo userInfo = iWxUserInfoService.findByOpenId(openId);
        Map<String,Object> map=new HashMap<>();
        map.put("userInfo",userInfo);
        return R.ok(map);
    }
    @RequestMapping("/detail")
    public R detail(HttpServletRequest request
            ,@RequestParam("id") Integer id
            ,@RequestParam("myOpenId") String myOpenId){
        Product product = productService.getById(id);
        WxUserInfo userInfo = iWxUserInfoService.findByOpenId(product.getOpenId());
        userInfo.setBrowse(userInfo.getBrowse()+1);
        iWxUserInfoService.updateById(userInfo);
        int isdescribe = attentionService.count(
                new QueryWrapper<Attention>()
                        .eq("user_id",myOpenId)
                        .eq("opposite_id",userInfo.getOpenid())
        );

        List<PayItem> payitemList = payItemService.list(
                new QueryWrapper<PayItem>().
                        eq("grade",userInfo.getEmployee_grade() )
                        .eq("required",0)
        );
        List<ExtraPayitem> extraPayitemList = extraPayitemService.list(
                new QueryWrapper<ExtraPayitem>()
                        .eq("employee_id",userInfo.getOpenid())
        );
        for(ExtraPayitem extraPayitem : extraPayitemList){
            PayItem payItem = payItemService.getById(extraPayitem.getPayitem_id());
            payitemList.add(payItem);
        }
        List<ProductSwiperImage> productSwiperImageList = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>().eq("productId", product.getId()).orderByAsc("sort"));
        product.setProductSwiperImageList(productSwiperImageList);
        Map<String,Object> map=new HashMap<>();
        map.put("payitemList",payitemList);
        map.put("message",product);
        map.put("userInfo",userInfo);
        map.put("isdescribe",isdescribe > 0? true : false);
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
        List<WxUserInfo> wxUserInfoListList = iWxUserInfoService.list(new QueryWrapper<WxUserInfo>().like("nickName", q).eq("admin",1));
        for(WxUserInfo userInfo: wxUserInfoListList){
            QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
            Product product =productService.getOne(
                    productQueryWrapper
                            .eq("openId",userInfo.getOpenid())
            );
            userInfo.setDetail(product);
        }
//        System.out.println("wxUserInfoListList--"+q+":"+wxUserInfoListList);
        Map<String,Object> map=new HashMap<>();
        map.put("message",wxUserInfoListList);
        return R.ok(map);
    }


    @GetMapping("/listImg/{id}")
    public R list(@PathVariable(value = "id") Integer id){
        List<ProductSwiperImage> list = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>().eq("productId",id));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("productSwiperImageList",list);
        return R.ok(resultMap);
    }
    @PostMapping("/addImg")
    public R add(@RequestBody ProductSwiperImage productSwiperImage){
        productSwiperImageService.saveOrUpdate(productSwiperImage);
        return R.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/deleteImg/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        productSwiperImageService.removeById(id);
        return R.ok();
    }


}
