package com.example.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.ProductSwiperImage;
import com.example.entity.R;
import com.example.service.IProductSwiperImageService;
import com.example.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员-产品轮播图Controller控制器
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-20 6:35
 */
@RestController
@RequestMapping("/admin/productSwiperImage")
public class AdminProductSwiperImageController {

    @Autowired
    private IProductSwiperImageService productSwiperImageService;

    @Value("${productSwiperImagesFilePath}")
    private String productSwiperImagesFilePath;

    /**
     * 根据条件分页查询
     * @return
     */
    @GetMapping("/list/{id}")
    public R list(@PathVariable(value = "id") Integer id){
        List<ProductSwiperImage> list = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>().eq("productId",id));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("productSwiperImageList",list);
        return R.ok(resultMap);
    }

    /**
     * 上传商品轮播图片
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

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(productSwiperImagesFilePath+newFileName));
            map.put("code", 0);
            map.put("msg", "上传成功");
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("title", newFileName);
            map2.put("src", "/image/productSwiperImgs/"+newFileName);
            map.put("data", map2);
        }
        return map;
    }

    /**
     * 添加
     * @param productSwiperImage
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody ProductSwiperImage productSwiperImage){
        productSwiperImageService.saveOrUpdate(productSwiperImage);
        return R.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        productSwiperImageService.removeById(id);
        return R.ok();
    }


}
