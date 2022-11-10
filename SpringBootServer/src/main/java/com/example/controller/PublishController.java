package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.mapper.PublishImgMapper;
import com.example.mapper.PublishLikeMapper;
import com.example.mapper.PublishReplyMapper;
import com.example.service.PublishService;
import com.example.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/publish")
public class PublishController {

    @Resource
    PublishService pulishService;

    @Resource
    PublishReplyMapper publishReplyMapper;

    @Resource
    PublishImgMapper publishImgMapper;

    @Resource
    PublishLikeMapper publishLikeMapper;

    @Value("${PublishImagesFilePath}")
    private String PublishImagesFilePath;

    @PostMapping("/add_publish")
    public R addPulish(@RequestBody PublishAndPublishImg addPublish, HttpServletRequest request){
        String openId = (String) request.getSession().getAttribute("openId");
        Publish publish =addPublish.getPublish();
        publish.setOpenId(openId);
        pulishService.add(publish,addPublish.getPublishImgs());
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("ok",200);
        return R.ok(resultMap);
    }

    @RequestMapping("/get_mypublish")
    public Map<String,Object> getMyPublish(HttpServletRequest request,@RequestBody PageBean pageBean){
        String openId=(String)request.getSession().getAttribute("openId");

        pageBean.setQuery(openId);
        List<Map<String,Object>> publishs = pulishService.getMyPublish(pageBean);
        Map map = new HashMap<String,Object>();
        map.put("publishs",publishs);
        return map;

    }

    @PostMapping("/addImg")
    public R add(@RequestBody PublishImg publishImg){
        publishImgMapper.insert(publishImg);
        return R.ok();
    }
    @GetMapping("/deleteImg/{id}")
    public R deleteImg(@PathVariable(value = "id") Integer id){
        publishImgMapper.deleteById(id);
        return R.ok();
    }
    @GetMapping("/listImg/{id}")
    public R listImg(@PathVariable(value = "id") Integer id){
        System.out.println(id);
        List<PublishImg> list = publishImgMapper.selectList (new QueryWrapper<PublishImg>().eq("pid",id));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("productSwiperImageList",list);
        return R.ok(resultMap);
    }

    @GetMapping ("/get_random")
    public Map<String,Object> geRandom(PageBean pageBean){
//        System.out.println(pageBean);
        List<Map<String,Object>> publishs = pulishService.getRandom(pageBean);
//        System.out.println(publishs.toString());
        Map map = new HashMap<String,Object>();
        map.put("publishs",publishs);
        return map;

    }
    @RequestMapping ("/add_reply")
    public Map<String,Object> addReply(HttpServletRequest request,PublishReply publishReply){
        publishReply.setOpenId((String) request.getSession().getAttribute("openId"));
        publishReplyMapper.insert(publishReply);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("ok",200);
        return R.ok(resultMap);

    }

    @RequestMapping ("/selectone")
    public Map<String,Object> selectOne(HttpServletRequest request,@Param("pid") int pid){
        Publish publish=pulishService.selectOneByPid(pid);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("publish",publish);
        return resultMap;

    }
    @RequestMapping ("/changlike")
    public Map<String,Object> changLike(HttpServletRequest request,
                                        @Param("pid") int pid){
        String openId=(String) request.getSession().getAttribute("openId");
        Map<String,Object> selectMap=new HashMap<String,Object>();
        selectMap.put("pid",pid);
        selectMap.put("openId",openId);
        List<PublishLike> list =publishLikeMapper.selectByMap(selectMap);
        if(list.size() > 0 ){
            for (PublishLike publishLike : list) {
                publishLikeMapper.deleteById(publishLike.getId());
            }
        }
        else{
            PublishLike publishLike = new PublishLike();
            publishLike.setPid(pid);
            publishLike.setOpenId(openId);
            publishLikeMapper.insert(publishLike);
        }

        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("ok",200);
        return R.ok(resultMap);

    }

    @RequestMapping("/uploadPublishImage")
    public Map<String,Object> uploadSwiperImage(MultipartFile file)throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        if(!file.isEmpty()){
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;

            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(PublishImagesFilePath+newFileName));
            map.put("code", 0);
            map.put("msg", "上传成功");
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("title", newFileName);
            map2.put("src", "/image/productIntroImgs/"+newFileName);
            map.put("data", map2);
        }
        return map;
    }
    @GetMapping("/delete/{id}")
    public R delete(HttpServletRequest request,@PathVariable(value = "id") Integer id){
        String openId=(String) request.getSession().getAttribute("openId");
        Publish publish=pulishService.getOne(
                new QueryWrapper<Publish>()
                        .eq("openId",openId)
                        .eq("pid",id)
        );
        if(publish !=null){
            publish.setStatus(3);
            pulishService.update(publish,
                    new QueryWrapper<Publish>()
                            .eq("pid",publish.getPid())
                    );
        }

        // 删除订单细表的数据
//        orderDetailService.remove(new QueryWrapper<OrderDetail>().eq("mId",id));
//        orderService.removeById(id);
        return R.ok();
    }
}
