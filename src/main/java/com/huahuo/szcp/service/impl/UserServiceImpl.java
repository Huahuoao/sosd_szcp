package com.huahuo.szcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huahuo.szcp.common.HttpCode;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.mapper.UserMapper;
import com.huahuo.szcp.pojo.User;
import com.huahuo.szcp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author Administrator
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-02-06 19:35:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> register(String phone, String password) {
        // 对密码进行加密存储
        String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();
        user.setPhone(phone);
        user.setNickName("默认昵称" + generateRandomString(5));
        user.setPassword(encodedPassword);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            return Result.error(405, null, "请勿重复注册");
        }
        return Result.success(user);
    }

    @Override
    public Result<User> login(String phone, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        if (user == null) {
            return Result.error(HttpCode.NOT_FOUND.getCode(), null, HttpCode.NOT_FOUND.getMessage());
        }
        // 验证密码是否匹配
        boolean passwordMatch = BCrypt.checkpw(password, user.getPassword());
        if (!passwordMatch) {
            return Result.error(HttpCode.UNAUTHORIZED.getCode(), null, HttpCode.UNAUTHORIZED.getMessage());
        }

        return Result.success(user);
    }
}




