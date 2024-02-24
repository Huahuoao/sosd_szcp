package com.huahuo.szcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.CommentRequest;
import com.huahuo.szcp.dto.CommentsVo;
import com.huahuo.szcp.dto.GetCollectionsDto;
import com.huahuo.szcp.mapper.CollectionMapper;
import com.huahuo.szcp.pojo.Collection;
import com.huahuo.szcp.service.CollectionService;
import com.huahuo.szcp.service.CommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Executor;

@Api("孵化池API")
@RestController
@RequestMapping("/api/v1/community")
public class CommunityController {

    @Autowired
    private Executor asyncServiceExecutor;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    CollectionService collectionService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private CollectionMapper collectionMapper;

    @ApiOperation("分页查询孵化池(未上链)藏品")
    @GetMapping("/get/collections")
    PageResult<List<Collection>> getCollections(@RequestBody GetCollectionsDto dto) {
        Page<Collection> pager = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<Collection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collection::getIsOnChain, false);
        if (dto.isSortByTime()) {
            queryWrapper.orderByDesc(Collection::getCreatedAt);
        } else {
            queryWrapper.orderByDesc(Collection::getHotValue);
        }
        if (StringUtils.isNotBlank(dto.getCategory())) {
            queryWrapper.eq(Collection::getCategory, dto.getCategory());
        }
        if (StringUtils.isNotBlank(dto.getText())) {
            queryWrapper.like(Collection::getName, dto.getText());
        }
        Page<Collection> collectionPage = collectionMapper.selectPage(pager, queryWrapper);
        return PageResult.success((int) collectionPage.getTotal(), 200, collectionPage.getRecords(), "数据获取成功");
    }


    @PostMapping("/comments/add")
    @ApiOperation("添加评论")
    public Result<String> addComment(@RequestBody CommentRequest commentRequest) {
        Integer userId = commentRequest.getUserId();
        Integer parentCommentId = commentRequest.getParentCommentId();
        String commentText = commentRequest.getCommentText();
        Integer collectionId = commentRequest.getCollectionId();
        boolean success = commentsService.addComment(userId, parentCommentId, commentText, collectionId);
        if (success) {
            asyncServiceExecutor.execute(() -> {
                Collection collection = collectionService.getById(collectionId);
                collection.setHotValue(collection.getHotValue() + 50);
                collection.setCommentsNum(collection.getCommentsNum() + 1);
                collectionService.updateById(collection);
            });
            return Result.success("评论添加成功");
        } else {
            return Result.error(500, null, "评论添加失败");
        }
    }

    @PostMapping("/comments/delete")
    @ApiOperation("删除评论")
    public Result<String> deleteComment(@RequestParam Integer commentId) {
        Collection collection = collectionService.getById(commentsService.getById(commentId).getCollectionId());
        asyncServiceExecutor.execute(() -> {
            collection.setHotValue(collection.getHotValue() - 50);
            collection.setCommentsNum(collection.getCommentsNum() - 1);
            collectionService.updateById(collection);
        });
        commentsService.removeById(commentId);
        return Result.success("删除评论成功");
    }

    @ApiOperation("查询评论")
    @GetMapping("/comments/list/comment")
    public PageResult<List<CommentsVo>> getNestedComments(@RequestParam Integer collectionId, @RequestParam int page, @RequestParam int size) {
        return commentsService.getNestedComments(collectionId, page, size);
    }

    @ApiOperation("对藏品点赞")
    @PostMapping("/like")
    public Result<Boolean> likeCollection(@RequestParam Integer userId, @RequestParam Integer collectionId) {
        String key = "like:" + userId + ":" + collectionId;
        Collection collection = collectionService.getById(collectionId);
        // 判断 key 是否存在
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            // 如果存在，则删除 key
            redisTemplate.delete(key);
            asyncServiceExecutor.execute(() -> {
                collection.setHotValue(collection.getHotValue() - 20);
                collection.setLikeNum(collection.getLikeNum() - 1);
                collectionService.updateById(collection);
            });
            return Result.success(false);
        } else {
            // 如果不存在，设置 key
            redisTemplate.opsForValue().set(key, "1");
            asyncServiceExecutor.execute(() -> {
                collection.setHotValue(collection.getHotValue() + 20);
                collection.setLikeNum(collection.getLikeNum() + 1);
                collectionService.updateById(collection);
            });
            return Result.success(true);
        }
    }

}
