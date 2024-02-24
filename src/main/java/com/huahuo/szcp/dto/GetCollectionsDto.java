package com.huahuo.szcp.dto;

import lombok.Data;

@Data
public class GetCollectionsDto {
    int page;
    int size;
    boolean sortByTime;
    String text;
    String category;
}
