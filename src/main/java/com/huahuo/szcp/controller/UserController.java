package com.huahuo.szcp.controller;

import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.UserDto;
import com.huahuo.szcp.pojo.User;
import com.huahuo.szcp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Api("用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<User> register(@RequestBody UserDto userDto) {
        String phone = userDto.getPhone();
        String password = userDto.getPassword();
        return userService.register(phone, password);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<User> login(@RequestBody UserDto userDto) {
        String phone = userDto.getPhone();
        String password = userDto.getPassword();
        return userService.login(phone, password);
    }
}
