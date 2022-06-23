package com.flower_mall.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flower_mall.system.common.Result;
import com.flower_mall.system.entity.Goods;
import com.flower_mall.system.mapper.GoodsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    GoodsMapper goodsMapper;

    @PostMapping
    public Result<?> save(@RequestBody Goods goods) {
        System.out.println("Insert: " + goods);
        if (goods.getImage1() == null) {
            goods.setImage1("http://localhost:9090/image/defaultImage.png");
        }
        goodsMapper.insert(goods);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Goods goods) {
        System.out.println("Update: " + goods);
        goodsMapper.update(goods, Wrappers.<Goods>lambdaQuery().eq(Goods::getId, goods.getId()));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable BigInteger id) {
        System.out.println("Delete: " + id);
        goodsMapper.delete(Wrappers.<Goods>lambdaQuery().eq(Goods::getId, id));
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        Page<Goods> userPage = goodsMapper.selectPage(
                new Page<>(pageNum, pageSize),
                Wrappers.<Goods>lambdaQuery().like(Goods::getGoodsNumber, search)
                        .like(Goods::getFlowerSpecies, search)
                        .like(Goods::getGoodsPrice, search));
        return Result.success(userPage);
    }

}
