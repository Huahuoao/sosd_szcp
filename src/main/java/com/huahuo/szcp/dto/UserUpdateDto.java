package com.huahuo.szcp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data

public class UserUpdateDto {
    Integer id;
    String headImg;
    String phone;
    BigDecimal balance;
    String nickName;
}
