package com.huahuo.szcp.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private Integer collectionId;
    private Integer buyerId;
    private Integer sellerId;

    // 省略 getter 和 setter
}
