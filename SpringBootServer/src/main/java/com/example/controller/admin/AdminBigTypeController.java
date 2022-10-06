package com.example.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.service.IBigTypeService;
import com.example.service.ISmallTypeService;
import com.example.util.DateUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理端-商品大类Controller控制器
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-10 7:50
 */
@RestController
@RequestMapping("/admin/bigType")
public class AdminBigTypeController {

    @Autowired
    private IBigTypeService bigTypeService;

    @Autowired
    private ISmallTypeService smallTypeService;

    @Value("${bigTypeImagesFilePath}")
    private String bigTypeImagesFilePath;

    /**
     * 分页查询
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        String query=pageBean.getQuery().trim();
        Page<BigType> page=new Page<>(pageBean.getPageNum(),pageBean.getPageSize());
        Page<BigType> pageResult = bigTypeService.page(page, new QueryWrapper<BigType>().like("name", query));
        Map<String,Object> map=new HashMap<>();
        map.put("bigTypeList",pageResult.getRecords());
        map.put("total",pageResult.getTotal());
        return R.ok(map);
    }

    /**
     * 查询所有数据 下拉框用到
     * @return
     */
    @RequestMapping("/listAll")
    public R listAll(){
        Map<String,Object> map=new HashMap<>();
        map.put("bigTypeList",bigTypeService.list());
        return R.ok(map);
    }

    /**
     * 添加或者修改
     * @param bigType
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody BigType bigType){
        if(bigType.getId()==null || bigType.getId()==-1){
            bigTypeService.save(bigType);
        }else{
            bigTypeService.saveOrUpdate(bigType);
        }
        return R.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") Integer id){
        // 加个判断 大类下面如果有小类，返回报错提示
        if(smallTypeService.count(new QueryWrapper<SmallType>().eq("bigTypeId",id))>0){
            return R.error(500,"大类下面有小类信息，不能删除");
        }else{
            bigTypeService.removeById(id);
            return R.ok();
        }
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable(value = "id") Integer id){
        System.out.println("id="+id);
        BigType bigType = bigTypeService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("bigType",bigType);
        return R.ok(map);
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

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(bigTypeImagesFilePath+newFileName));
            map.put("code", 0);
            map.put("msg", "上传成功");
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("title", newFileName);
            map2.put("src", "/image/bigType/"+newFileName);
            map.put("data", map2);

/*

            BigType bigType = bigTypeService.getById(id);
            bigType.setImage(newFileName);
            bigTypeService.saveOrUpdate(bigType);
*/

        }

        return map;
    }



}
