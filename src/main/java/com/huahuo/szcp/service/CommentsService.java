package com.huahuo.szcp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.dto.CommentsVo;
import com.huahuo.szcp.pojo.Comments;

import java.util.List;

/**
* @author Administrator
* @description 针对表【comments】的数据库操作Service
* @createDate 2024-02-11 16:12:57
*/
public interface CommentsService extends IService<Comments> {
    public PageResult<List<CommentsVo>> getNestedComments(Integer collectionId, Integer page, Integer size);
    boolean addComment(Integer userId, Integer parentCommentId, String commentText, Integer collectionId);
}
