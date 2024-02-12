package com.huahuo.szcp.controller;

import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.CommentRequest;
import com.huahuo.szcp.dto.CommentsVo;
import com.huahuo.szcp.pojo.User;
import com.huahuo.szcp.service.CommentsService;
import com.huahuo.szcp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@Api("评论相关接口")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;
    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation("添加评论")
    public Result<String> addComment(@RequestBody CommentRequest commentRequest) {
        Integer userId = commentRequest.getUserId();
        Integer parentCommentId = commentRequest.getParentCommentId();
        String commentText = commentRequest.getCommentText();
        Integer collectionId = commentRequest.getCollectionId();

        boolean success = commentsService.addComment(userId, parentCommentId, commentText, collectionId);
        if (success) {
            return Result.success("评论添加成功");
        } else {
            return Result.error(500, null, "评论添加失败");
        }
    }

    @ApiOperation("查询评论")
    @GetMapping("/list/comment")
    public PageResult<List<CommentsVo>> getNestedComments(@RequestParam Integer collectionId, @RequestParam int page, @RequestParam int size) {
        return commentsService.getNestedComments(collectionId, page, size);
    }
}
