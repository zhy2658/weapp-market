package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
public class Wxpaynotifyvo {
    @TableId(value = "wxpaynotifyvo")
    private int wxpaynotifyvo;
    private String appid;
    private String attach;
    private String bank_type;
    @TableField(value = "fee_type")
    private String fee_type;
    private String is_subscribe;
    private String mch_id;
    private String nonce_str;
    private String openid;
    private String out_trade_no;
    private String result_code;
    private String return_code;
    private String sign;
    private String time_end;
    private String total_fee;
    private String coupon_fee;
    private String coupon_count;
    private String coupon_type;
    private String coupon_id;
    private String trade_id;
    private String transaction_id;
    private int activityid;
    private String transactionid;
    private String totalfee;
}
