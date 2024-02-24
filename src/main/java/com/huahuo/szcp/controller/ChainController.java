package com.huahuo.szcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huahuo.szcp.common.PageResult;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.GetCollectionOwnDto;
import com.huahuo.szcp.dto.GetCollectionsDto;
import com.huahuo.szcp.dto.TransactionDTO;
import com.huahuo.szcp.mapper.CollectionMapper;
import com.huahuo.szcp.pojo.Collection;
import com.huahuo.szcp.service.CollectionService;
import com.huahuo.szcp.service.TransactionRecordService;
import com.huahuo.szcp.utils.BlockChainUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api("上链相关API")
@RestController
@RequestMapping("/api/v1/chain")
public class ChainController {
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    private TransactionRecordService transactionService;
    @Autowired
    CollectionService collectionService;

    @ApiOperation("查看自己链上资产")
    @GetMapping("/get/chain/assets")
    PageResult<List<Collection>> getCollectionsOwn(@RequestBody GetCollectionOwnDto dto) {
        Page<Collection> pager = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<Collection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collection::getIsOnChain, true);
        queryWrapper.eq(Collection::getOwner, dto.getUserId());
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

    @ApiOperation("查询链上藏品")
    @GetMapping("/get/collections")
    PageResult<List<Collection>> getCollections(@RequestBody GetCollectionsDto dto) {
        Page<Collection> pager = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<Collection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collection::getIsOnChain, true);
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

    @ApiOperation("藏品上链")
    @PostMapping("/onChain")
    public Result<Boolean> onChain(@RequestParam Integer id, @RequestParam Integer quantity, @RequestParam Double price) {
        Collection collection = collectionService.getById(id);
        collection.setIsOnChain(true);
        collection.setPrice(BigDecimal.valueOf(price));
        collection.setQuantity(quantity);
        collection.setBlockchainAddress(BlockChainUtils.generateBitcoinAddress());
        collectionService.save(collection);
        return Result.success(true);
    }

    @ApiOperation("进行交易")
    @PostMapping("/transaction")
    public Result<String> addTransactionRecord(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.addTransactionRecord(transactionDTO.getCollectionId(), transactionDTO.getBuyerId(), transactionDTO.getSellerId());
    }
}
