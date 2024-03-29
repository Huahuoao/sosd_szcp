package com.huahuo.szcp.controller;

import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.CollectionDTO;
import com.huahuo.szcp.dto.CreateCollectionDto;
import com.huahuo.szcp.pojo.Collection;
import com.huahuo.szcp.service.CollectionService;
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
        return collectionService.toggleCollection(collectionDTO.getCollectionId(), collectionDTO.getUserId());
    }

    @ApiOperation("创建新藏品")
    @PostMapping("/create")
    public Result<Boolean> createCollection(@RequestBody CreateCollectionDto dto) {
        return collectionService.createCollection(dto);
    }

    @ApiOperation("获得用户拥有藏品")
    @GetMapping("/get/byOwner")
    public PageResult<List<Collection>> getCollectionByOwner(@RequestParam Integer userId, @RequestParam Integer page, @RequestParam Integer size) {
        return collectionService.getCollectionByOwner(userId, page, size);
    }


    @ApiOperation("获得用户创建的藏品")
    @GetMapping("/get/byCreator")
    public PageResult<List<Collection>> getCollectionByCreator(@RequestParam Integer userId, @RequestParam Integer page, @RequestParam Integer size) {
        return collectionService.getCollectionByCreator(userId, page, size);
    }



}
