package com.flower_mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flower_mall.system.entity.Goods;
import com.flower_mall.system.entity.Order;
import com.flower_mall.system.entity.Order_Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("${sqlStr}")
    List<Order_Goods> dynamicSql(@Param("sqlStr") String sql);
}
