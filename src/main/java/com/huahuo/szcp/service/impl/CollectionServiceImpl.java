package com.huahuo.szcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.CreateCollectionDto;
import com.huahuo.szcp.mapper.CollectionFavoriteMapper;
import com.huahuo.szcp.mapper.CollectionMapper;
import com.huahuo.szcp.pojo.Collection;
import com.huahuo.szcp.pojo.CollectionFavorite;
import com.huahuo.szcp.service.CollectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 * @description 针对表【collection(藏品信息表)】的数据库操作Service实现
 * @createDate 2024-02-06 20:44:04
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection>
        implements CollectionService {
    @Autowired
    private CollectionFavoriteMapper collectionFavoriteMapper;
    @Autowired
    private CollectionMapper collectionMapper;

    public Result<Boolean> toggleCollection(Integer collectionId, Integer collectorId) {
        QueryWrapper<CollectionFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id", collectionId);
        queryWrapper.eq("collector_id", collectorId);
        CollectionFavorite collectionFavorite = collectionFavoriteMapper.selectOne(queryWrapper);

        if (collectionFavorite != null) {
            // 如果已经收藏了，则删除该收藏记录
            int rows = collectionFavoriteMapper.deleteById(collectionFavorite.getId());
            return Result.success(rows > 0);
        } else {
            // 如果还未收藏，则插入一条新的收藏记录
            CollectionFavorite newCollectionFavorite = new CollectionFavorite();
            newCollectionFavorite.setCollectionId(collectionId);
            newCollectionFavorite.setCollectorId(collectorId);
            int rows = collectionFavoriteMapper.insert(newCollectionFavorite);
            return Result.success(rows > 0);
        }
    }


    public PageResult<List<Collection>> getCollectionByOwner(Integer userId, Integer page, Integer size) {
        Page<Collection> pager = new Page<>(page, size);
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner", userId).orderByDesc("updated_at");
        Page<Collection> collectionPage = collectionMapper.selectPage(pager, queryWrapper);
        return PageResult.success((int) collectionPage.getTotal(), 200, collectionPage.getRecords(), "数据获取成功");
    }

    public PageResult<List<Collection>> getCollectionAll(Integer page, Integer size) {
        Page<Collection> pager = new Page<>(page, size);
        Page<Collection> collectionPage = collectionMapper.selectPage(pager, new LambdaQueryWrapper<Collection>().orderByDesc(Collection::getUpdatedAt));
        return PageResult.success((int) collectionPage.getTotal(), 200, collectionPage.getRecords(), "数据获取成功");
    }

    public PageResult<List<Collection>> getCollectionByCreator(Integer userId, Integer page, Integer size) {
        Page<Collection> pager = new Page<>(page, size);
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creator", userId).orderByDesc("updated_at");
        Page<Collection> collectionPage = collectionMapper.selectPage(pager, queryWrapper);
        return PageResult.success((int) collectionPage.getTotal(), 200, collectionPage.getRecords(), "数据获取成功");
    }

    @Override
    public Result<Boolean> createCollection(CreateCollectionDto dto) {
        Collection collection = new Collection();
        BeanUtils.copyProperties(dto, collection);
        collection.setOwner(collection.getCreator());
        collection.setIsOnChain(false);
        collection.setIsAi(dto.getIsAi());
        Random random = new Random();
        collection.setHotValue(random.nextInt(20));
        this.save(collection);
        return Result.success(true);
    }
}




