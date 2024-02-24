package com.huahuo.szcp.dto;

import lombok.Data;

@Data
public class CreateCollectionDto {
    String name;
    String imageUrl;
    String description;
    Integer creator;
    Boolean isAi;
}
