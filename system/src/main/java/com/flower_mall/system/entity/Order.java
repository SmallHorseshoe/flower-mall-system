package com.flower_mall.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;

/**
 * 订单实体类
 */
@TableName("flower_order")
@Data
public class Order {
    @TableId(type = IdType.AUTO)
    private String id;
    private String goodsId;
    private Double sellerPhone;
    private BigInteger buyerPhone;
    private BigInteger sellNumber;
    private Integer amount;
    private String receiveAddress;
    private String orderTime;
    private String deliveryTime;
    private String completeTime;
}
