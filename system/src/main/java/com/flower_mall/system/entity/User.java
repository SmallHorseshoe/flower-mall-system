package com.flower_mall.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;

/**
 * 用户信息实体类
 */
@TableName("user")
@Data
public class User {
//    @TableId(value = "id",type = IdType.AUTO)
    private BigInteger userPhone;
    private String nickname;
    private String password;
    private String userName;
    private String idCard;
    private String sex;
    private String signature;
    private String userAddress;
    private String avatar;
}
