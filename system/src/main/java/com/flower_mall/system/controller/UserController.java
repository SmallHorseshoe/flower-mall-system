package com.flower_mall.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flower_mall.system.common.Result;
import com.flower_mall.system.entity.User;
import com.flower_mall.system.mapper.UserMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Wrapper;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {

    //    @Autowired
    @Resource
    UserMapper userMapper;

    @PostMapping("/adminLogin")
    public Result<?> adminLogin(@RequestBody User user) {
//        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()).eq(User::getPassword, user.getPassword()));
//        if (res == null) {
//            return Result.error("-1", "用户名或密码错误");
//        }
        if (user.getUserName().equals("admin") && user.getPassword().equals("123456")) {
            return Result.success();
        }
        return Result.error("-1", "用户名或密码错误");
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserPhone, user.getUserPhone()).eq(User::getPassword, user.getPassword()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        System.out.println("Login: " + user.getUserPhone());
        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody User user) {
        User user1 = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserPhone, user.getUserPhone()));
        if (user1 != null) {
            return Result.error("-1", "该账号已存在！");
        }

        System.out.println("Insert: " + user);
        if (user.getAvatar() == null) {
            user.setAvatar("http://localhost:9090/image/defaultUser.png");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        System.out.println("Update: " + user);
        userMapper.update(user, Wrappers.<User>lambdaQuery().eq(User::getUserPhone, user.getUserPhone()));
        return Result.success();
    }

    @DeleteMapping("/{userPhone}")
    public Result<?> update(@PathVariable BigInteger userPhone) {
        System.out.println("Delete: " + userPhone);
        userMapper.delete(Wrappers.<User>lambdaQuery().eq(User::getUserPhone, userPhone));
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        Page<User> userPage = userMapper.selectPage(
                new Page<>(pageNum, pageSize),
                Wrappers.<User>lambdaQuery()
                        .like(User::getUserName, search)
                        .like(User::getUserPhone, search)
                        .like(User::getIdCard, search)
        );
        return Result.success(userPage);
    }

    @GetMapping({"/userInfo"})
    public Result<?> getUserInfo(@RequestParam(defaultValue = "1") BigInteger userPhone) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserPhone, userPhone));
        if (res == null) {
            return Result.error("-1", "请重新登录");
        }
        System.out.println("getUserInfo: " + res);
        return Result.success(res);
    }
}
