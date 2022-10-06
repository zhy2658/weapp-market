package com.example.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.service.IProductService;
import com.example.service.ISmallTypeService;
import com.example.service.IWxUserInfoService;
import com.example.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员-产品Controller控制器
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-18 8:26
 */
@RestController
@RequestMapping("/admin/product")
public class AdminProductController {

    @Autowired
    private IProductService productService;

    @Resource
    IWxUserInfoService iWxUserInfoService;

    @Resource
    private ISmallTypeService smallTypeService;

    @Value("${productImagesFilePath}")
    private String productImagesFilePath;

    @Value("${swiperImagesFilePath}")
    private String swiperImagesFilePath;

    /**
     * 根据条件分页查询
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        Map<String,Object> map=new HashMap<>();
        map.put("name",pageBean.getQuery().trim());
        map.put("start",pageBean.getStart());
        map.put("pageSize",pageBean.getPageSize());
        List<Map<String,Object>> list = productService.list(map);


        for(int i=0;i<list.size();i++){
            Product product =(Product) list.get(i);

            Integer small_id = product.getUserInfo().getSmall_id();

            if(small_id == null) continue;
            QueryWrapper<SmallType> queryWrapper = new QueryWrapper<SmallType>();
            List<SmallType> smallTypeList= smallTypeService.list(queryWrapper.eq("id",small_id));
            product.getUserInfo().setSmallType(smallTypeList.get(0));
//            list.get(i).put("smallTypeList",smallTypeList);

        }

        Long total = productService.getTotal(map);

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("productList",list);
        resultMap.put("total",total);
        return R.ok(resultMap);
    }

    /**
     * 更新热门状态
     * @param id
     * @param hot
     * @return
     */
    @GetMapping("/updateHot/{id}/state/{hot}")
    public R updateHot(@PathVariable(value = "id") Integer id,@PathVariable(value = "hot") boolean hot){
        Product product = productService.getById(id);
        product.setHot(hot);
        if(hot){
            product.setHotDateTime(new Date());
        }else{
            product.setHotDateTime(null);
        }
        productService.saveOrUpdate(product);
        return R.ok();
    }

    /**
     * 更新swiper状态
     * @param id
     * @param swiper
     * @return
     */
    @GetMapping("/updateSwiper/{id}/state/{swiper}")
    public R updateSwiper(@PathVariable(value = "id") Integer id,@PathVariable(value = "swiper") boolean swiper){
        Product product = productService.getById(id);
        product.setSwiper(swiper);
        productService.saveOrUpdate(product);
        return R.ok();
    }

    /**
     * 更新图片
     * @param product
     * @return
     */
    @PostMapping("/saveImage")
    public R saveImage(@RequestBody Product product){
        System.out.println("projuct-------receive---------");
        WxUserInfo userInfo = product.getUserInfo();
        iWxUserInfoService.update(userInfo);
//        Product p = productService.getById(product.getId());
//        p.setProPic(product.getProPic());
//        productService.saveOrUpdate(p);
        return R.ok();
    }

    /**
     * 更新swiper幻灯图片信息
     * @param product
     * @return
     */
    @PostMapping("/saveSwiper")
    public R saveSwiper(@RequestBody Product product){
        Product p = productService.getById(product.getId());
        p.setSwiperPic(product.getSwiperPic());
        p.setSwiperSort(product.getSwiperSort());
        productService.saveOrUpdate(p);
        return R.ok();
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        productService.removeById(id);
        return R.ok();
    }

    /**
     * 上传商品大类图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadImage")
    public Map<String,Object> uploadImage(MultipartFile file)throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        if(!file.isEmpty()){
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName=DateUtil.getCurrentDateStr()+suffixName;

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(productImagesFilePath+newFileName));
            map.put("code", 0);
            map.put("msg", "上传成功");
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("title", newFileName);
            map2.put("src", "/image/product/"+newFileName);
            map.put("data", map2);
        }
        return map;
    }


    /**
     * 上传swiper幻灯图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadSwiperImage")
    public Map<String,Object> uploadSwiperImage(MultipartFile file)throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        if(!file.isEmpty()){
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName=DateUtil.getCurrentDateStr()+suffixName;

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(swiperImagesFilePath+newFileName));
            map.put("code", 0);
            map.put("msg", "上传成功");
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("title", newFileName);
            map2.put("src", "/image/swiper/"+newFileName);
            map.put("data", map2);
        }
        return map;
    }

    /**
     * 添加或者修改
     * @param product
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody Product product){
        if(product.getId()==null || product.getId()==-1){ // 添加
            productService.add(product);
        }else{
            WxUserInfo userInfo = product.getUserInfo();
            iWxUserInfoService.update(userInfo);
            System.out.println(userInfo.toString());
            productService.update(product);
        }
        return R.ok();
    }




}
