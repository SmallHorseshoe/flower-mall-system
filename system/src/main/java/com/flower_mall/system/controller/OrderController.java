package com.flower_mall.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flower_mall.system.common.Result;
import com.flower_mall.system.entity.Order;
import com.flower_mall.system.entity.Order_Goods;
import com.flower_mall.system.mapper.OrderMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

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

    //    SELECT flower_order.id, goods_id, flower_order.seller_phone, buyer_phone, flower_order.sell_number, amount, receive_address, order_time, delivery_time, complete_time
    //    FROM `flower_order`, `goods` WHERE flower_order.buyer_phone='12345654321' AND goods.id = flower_order.goods_id
    @GetMapping("/orderInfo")
    public Result<?> getOrderInfo(@RequestParam(defaultValue = "1") BigInteger userPhone) {
        String sql = "SELECT * " + "FROM `flower_order`, `goods`WHERE flower_order.buyer_phone = '" + userPhone + "' AND goods.id = flower_order.goods_id ORDER BY order_time DESC";
        List<Order_Goods> orderList = orderMapper.dynamicSql(sql);
        System.out.println("getOrderInfo SQL: " + sql);
        System.out.println("getOrderInfo: " + orderList);
        return Result.success(orderList);
    }

}
