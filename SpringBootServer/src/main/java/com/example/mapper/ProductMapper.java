package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 商品Mapper接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 8:53
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询轮播商品
     * @return
     */
    public List<Product> findSwiper();

    /**
     * 查询热卖商品
     * @return
     */
    @Select(" SELECT u.openid,u.sex,u.age,u.nickName,u.avatarUrl ,p.description,p.audio,p.audioTime " +
            ",u.tags,p.id as detail_id " +
            "FROM t_wxuserinfo as u inner join  t_product as p " +
            "on u.openid = p.openId " +
            "WHERE p.isHot=1 and u.isshow=1  and admin = 1  ORDER BY registerDate DESC  LIMIT 0,8")
    public List<Map<String,Object>> findHot();

    /**
     * 根据条件 分页商品
     * @param map
     * @return
     */
//    <select id="list" parameterType="Map" resultMap="ProductResult">
//    select * from t_product
//        <where>
//            <if test="name!=null and name!='' ">
//    and name like concat('%',#{name},'%')
//            </if>
//        </where>
//        <if test="start!=null and pageSize!=null">
//    limit #{start},#{pageSize}
//        </if>
//    </select>

//    @Select("select * from t_product limit #{start},#{pageSize}")
    public List<Map<String,Object>> list(Map<String,Object> map);

    /**
     * 根据条件，查询商品总记录数
     * @param map
     * @return
     */
    public Long getTotal(Map<String,Object> map);

    /**
     * 添加商品
     * @param product
     * @return
     */
    public Integer add(Product product);

    /**
     * 修改商品
     * @param product
     * @return
     */
    public Integer update(Product product);


//    @Update("update t_product set audio=#{audio},audioTime=#{audioTime} where ")
//    public Integer updateByOpenId(@Param("openId") String openId,Product product);


}
