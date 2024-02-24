package com.huahuo.szcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.dto.CommentsVo;
import com.huahuo.szcp.mapper.CommentsMapper;
import com.huahuo.szcp.pojo.Comments;
import com.huahuo.szcp.pojo.User;
import com.huahuo.szcp.service.CommentsService;
import com.huahuo.szcp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【comments】的数据库操作Service实现
 * @createDate 2024-02-11 16:12:57
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
        implements CommentsService {
    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    UserService userService;

    public PageResult<List<CommentsVo>> getNestedComments(Integer collectionId, Integer page, Integer size) {
        Page<Comments> pager = new Page<>(page, size);
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("parent_comment_id").eq("collection_id", collectionId);
        IPage<Comments> commentPage = commentsMapper.selectPage(pager, queryWrapper);
        List<CommentsVo> nestedComments = new ArrayList<>();
        for (Comments comment : commentPage.getRecords()) {
            CommentsVo vo = new CommentsVo();
            BeanUtils.copyProperties(comment, vo);
            vo.setReplies(getNestedComments(collectionId, comment.getId()));
            nestedComments.add(vo);
        }
        return PageResult.success((int) commentPage.getTotal(), 200, nestedComments, "数据获取成功");
    }

    //递归调用
    private List<CommentsVo> getNestedComments(Integer collectionId, Integer parentCommentId) {
        List<CommentsVo> nestedComments = new ArrayList<>();
        List<Comments> directReplies = list(new LambdaQueryWrapper<Comments>().eq(Comments::getParentCommentId, parentCommentId).eq(Comments::getCollectionId, collectionId));
        for (Comments reply : directReplies) {
            CommentsVo vo = new CommentsVo();
            BeanUtils.copyProperties(reply, vo);
            vo.setReplies(getNestedComments(collectionId, reply.getId()));
            nestedComments.add(vo);
        }
        return nestedComments;
    }

    public boolean addComment(Integer userId, Integer parentCommentId, String commentText, Integer collectionId) {
        LocalDateTime now = LocalDateTime.now();
        Comments comment = new Comments();
        comment.setUserId(userId);
        User user = userService.getById(userId);
        comment.setNickName(user.getNickName());
        comment.setHeadImg(user.getHeadImg());
        if (parentCommentId != null)
            comment.setParentCommentId(parentCommentId);
        comment.setCommentText(commentText);
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        comment.setCollectionId(collectionId);

        return this.save(comment);
    }
}




