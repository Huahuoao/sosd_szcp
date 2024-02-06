package com.huahuo.szcp.service;

import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.pojo.TransactionRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【transaction_record】的数据库操作Service
* @createDate 2024-02-06 20:44:04
*/
public interface TransactionRecordService extends IService<TransactionRecord> {

    Result<String> addTransactionRecord(Integer productId, Integer buyerId, Integer sellerId);
}
