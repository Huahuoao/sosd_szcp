package com.huahuo.szcp.controller;

import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.dto.TransactionDTO;
import com.huahuo.szcp.service.TransactionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@Api("交易相关接口")
public class TransactionController {
    @Autowired
    private TransactionRecordService transactionService;

    @ApiOperation("进行交易")
    @PostMapping("/transaction")
    public Result<String> addTransactionRecord(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.addTransactionRecord(transactionDTO.getCollectionId(), transactionDTO.getBuyerId(), transactionDTO.getSellerId());
    }
}
