package com.huahuo.szcp.controller;

import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.CollectionDTO;
import com.huahuo.szcp.pojo.Collection;
import com.huahuo.szcp.service.CollectionService;
import com.huahuo.szcp.utils.BlockChainUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/collection")
@Api("藏品相关接口")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @ApiOperation("收藏藏品/取消收藏")
    @PostMapping("/like")
    public Result<Boolean> toggleCollection(@RequestBody CollectionDTO collectionDTO) {
        return collectionService.toggleCollection(collectionDTO.getCollectionId(), collectionDTO.getCollectorId());
    }

    @ApiOperation("创建新藏品")
    @PostMapping("/create")
    public Result<Boolean> createCollection(@RequestBody Collection collection) {
        return collectionService.createCollection(collection);
    }

    @ApiOperation("获得用户拥有藏品")
    @GetMapping("/get/byOwner")
    public PageResult<List<Collection>> getCollectionByOwner(@RequestParam Integer userId, @RequestParam Integer page, @RequestParam Integer size) {
        return collectionService.getCollectionByOwner(userId, page, size);
    }

    @ApiOperation("获得所有藏品")
    @GetMapping("/get/all")
    public PageResult<List<Collection>> getCollectionAll(@RequestParam Integer page, @RequestParam Integer size) {
        return collectionService.getCollectionAll(page, size);
    }

    @ApiOperation("获得用户创建的藏品")
    @GetMapping("/get/byCreator")
    public PageResult<List<Collection>> getCollectionByCreator(@RequestParam Integer userId, @RequestParam Integer page, @RequestParam Integer size) {
        return collectionService.getCollectionByCreator(userId, page, size);
    }

    @ApiOperation("藏品上链")
    @PostMapping("/onChain")
    public Result<Boolean> onChain(@RequestParam Integer id) {
        Collection collection = collectionService.getById(id);
        collection.setIsOnChain(true);
        collection.setBlockchainAddress(BlockChainUtils.generateBitcoinAddress());
        collectionService.save(collection);
        return Result.success(true);
    }

}
