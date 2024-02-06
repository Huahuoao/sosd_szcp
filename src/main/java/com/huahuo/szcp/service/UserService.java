package com.huahuo.szcp.service;

import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Administrator
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-02-06 19:35:43
 */
public interface UserService extends IService<User> {
    Result<User> register(String phone, String password);
    Result<User> login(String phone, String password);
}
