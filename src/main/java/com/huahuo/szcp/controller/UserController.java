package com.huahuo.szcp.controller;

import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.UserDto;
import com.huahuo.szcp.dto.UserUpdateDto;
import com.huahuo.szcp.pojo.User;
import com.huahuo.szcp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    public Result<Boolean> updateDetail(@RequestBody UserUpdateDto user) {
        User realUser = userService.getById(user.getId());
        realUser.setPhone(user.getPhone());
        realUser.setBalance(user.getBalance());
        realUser.setNickName(user.getNickName());
        realUser.setHeadImg(user.getHeadImg());
        userService.updateById(realUser);
        return Result.success(true);
    }

    @ApiOperation("通过id获取用户基本信息")
    @GetMapping("/get/byId")
    public Result<UserUpdateDto> getById(@RequestParam Integer id) {
        User byId = userService.getById(id);
        UserUpdateDto dto = new UserUpdateDto();
        dto.setId(byId.getId());
        dto.setPhone(byId.getPhone());
        dto.setBalance(byId.getBalance());
        dto.setHeadImg(byId.getHeadImg());
        dto.setNickName(byId.getNickName());
        return Result.success(dto);
    }
}
