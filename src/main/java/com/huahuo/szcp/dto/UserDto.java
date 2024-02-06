package com.huahuo.szcp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    String phone;
    String password;
}
