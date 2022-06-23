package com.flower_mall.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flower_mall.system.common.Result;
import com.flower_mall.system.entity.Goods;
import com.flower_mall.system.entity.Order;
import com.flower_mall.system.mapper.GoodsMapper;
import com.flower_mall.system.mapper.OrderMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderMapper orderMapper;

    @PostMapping
    public Result<?> save(@RequestBody Order order) {
        System.out.println("Insert: " + order);
        orderMapper.insert(order);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Order order) {
        System.out.println("Update: " + order);
        orderMapper.update(order, Wrappers.<Order>lambdaQuery().eq(Order::getId, order.getId()));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable BigInteger id) {
        System.out.println("Delete: " + id);
        orderMapper.delete(Wrappers.<Order>lambdaQuery().eq(Order::getId, id));
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        Page<Order> userPage = orderMapper.selectPage(
                new Page<>(pageNum, pageSize),
                Wrappers.<Order>lambdaQuery().like(Order::getId, search)
        );
        return Result.success(userPage);
    }

}
