package com.huahuo.szcp.dto;

import com.huahuo.szcp.pojo.Comments;
import lombok.Data;

import java.util.List;

@Data
public class CommentsVo extends Comments {
    private List<CommentsVo> replies;

}
