package com.flower_mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flower_mall.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

