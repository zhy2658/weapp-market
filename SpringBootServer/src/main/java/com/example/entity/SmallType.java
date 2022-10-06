package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品小类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-22 22:03
 */
@TableName("t_smalltype")
@Data
public class SmallType implements Serializable {

    private Integer id; // 编号

    private String name; // 名称

    private String remark; // 备注

    private Integer bigTypeId; // 大类id

    @TableField(select = false)
    private BigType bigType;  // 所属商品大类

    @TableField(select = false)
    private List<Product> productList; // 商品集合

    public BigType getBigType() {
        return bigType;
    }

    public void setBigType(BigType bigType) {
        this.bigType = bigType;
    }



    @TableField(select = false)
    private List<WxUserInfo> userInfos;  // 下面用户
}
