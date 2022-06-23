package com.flower_mall.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;

/**
 * 货物实体类
 */
@TableName("goods")
@Data
public class Goods {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String goodsName;
    private Double goodsPrice;
    private String sellState;
    private BigInteger sellerPhone;
    private Integer goodsNumber;
    private Integer sellNumber;
    private String flowerSpecies;
    private String goodsAddress;
    private String description;
    private String image1;
    private String image2;
    private String image3;
}
