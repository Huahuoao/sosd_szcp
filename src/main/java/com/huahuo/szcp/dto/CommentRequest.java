package com.huahuo.szcp.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Integer userId;
    private Integer parentCommentId;
    private String commentText;
    private Integer collectionId;
    
    // 构造函数、getter和setter方法省略
}
