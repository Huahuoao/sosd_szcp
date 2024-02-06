package com.huahuo.szcp.service;

import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.pojo.Collection;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【collection(藏品信息表)】的数据库操作Service
 * @createDate 2024-02-06 20:44:04
 */
public interface CollectionService extends IService<Collection> {

    Result<Boolean> toggleCollection(Integer collectionId, Integer collectorId);

    public Result<Boolean> createCollection(@RequestBody Collection collection);

    public PageResult<List<Collection>> getCollectionByOwner(Integer userId, Integer page, Integer size);
    public PageResult<List<Collection>> getCollectionAll(Integer page, Integer size);
    public PageResult<List<Collection>> getCollectionByCreator(Integer userId,Integer page, Integer size);

}
