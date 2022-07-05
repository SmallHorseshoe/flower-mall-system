package com.flower_mall.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;

/**
 * 订单信息和商品信息的联合数据
 */
@TableName("flower_order")
@Data
public class Order_Goods {
    @TableId(type = IdType.AUTO)
    private BigInteger id;
    private BigInteger goodsId;
    private String goodsName;
    private BigInteger sellerPhone;
    private BigInteger buyerPhone;
    private Integer sellNumber;
    private Double amount;
    private String receiveAddress;
    private String orderTime;
    private String deliveryTime;
    private String completeTime;
    private String image1;
    private String image2;
    private String image3;

}
