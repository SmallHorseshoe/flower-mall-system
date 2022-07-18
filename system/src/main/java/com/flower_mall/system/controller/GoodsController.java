package com.flower_mall.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flower_mall.system.common.Result;
import com.flower_mall.system.entity.Goods;
import com.flower_mall.system.mapper.GoodsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Wrapper;
import java.util.LinkedList;
import java.util.List;

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
                Wrappers.<Goods>lambdaQuery().like(Goods::getGoodsName, search));
//                        .like(Goods::getFlowerSpecies, search)
//                        .like(Goods::getGoodsPrice, search));
        return Result.success(userPage);
    }

    @GetMapping("/list")
    public Result<?> goodList(
            @RequestParam(defaultValue = "") String species,
            @RequestParam(defaultValue = "商品名称") String searchType,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String sortType
    ) {
        String[] flowerSpecies = species.split(":");
        StringBuilder sql = new StringBuilder("SELECT * FROM `goods` WHERE sell_state='上架中' AND ");
        // 筛查花卉品种
        if (flowerSpecies.length > 0) {
            sql.append("(");
            for (int i = 0; i < flowerSpecies.length; i++) {
                if (i != 0) sql.append(" or ");
                sql.append("flower_species like '%").append(flowerSpecies[i]).append("%'");
            }
            sql.append(")");
        }
        if (searchType.equals("商品名称")) {
            sql.append(" AND goods_name like '%").append(search).append("%'");
        } else if (searchType.equals("商家名称")) {
            sql.append(" AND seller_phone in").append("(select user_phone FROM `user` where nickname LIKE '%").append(search).append("%')");
        }
        switch (sortType) {
            case "按销量升序":
                sql.append(" order by sell_number ASC");
                break;
            case "按销量降序":
                sql.append(" order by sell_number DESC");
                break;
            case "按价格升序":
                sql.append(" order by goods_price ASC");
                break;
            case "按价格降序":
                sql.append(" order by goods_price DESC");
                break;
            default:
                break;
        }
        System.out.println("SQL: " + sql);
        List<Goods> goodsList = goodsMapper.dynamicSql(String.valueOf(sql));
        return Result.success(goodsList);
    }

    @GetMapping("/species")
    public Result<?> getSpecies() {
        List<String> res = goodsMapper.dynamicSqlForString("SELECT DISTINCT flower_species FROM goods");
        return Result.success(res);
    }


    @GetMapping("/goodsInfo")
    public Result<?> getGoodsInfo(@RequestParam(defaultValue = "1") BigInteger userPhone) {
        List<Goods> goodsList = goodsMapper.selectList(
                Wrappers.<Goods>lambdaQuery()
                        .eq(Goods::getSellerPhone, userPhone)
                        .orderBy(true, true, Goods::getSellState)
        );
        if (goodsList == null) {
            return Result.error("-1", "暂无商品");
        }
        System.out.println("getGoodsInfo: " + goodsList);
        return Result.success(goodsList);
    }
}
