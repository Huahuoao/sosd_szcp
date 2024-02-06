package com.huahuo.szcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.mapper.CollectionMapper;
import com.huahuo.szcp.mapper.TransactionRecordMapper;
import com.huahuo.szcp.mapper.UserMapper;
import com.huahuo.szcp.pojo.Collection;
import com.huahuo.szcp.pojo.TransactionRecord;
import com.huahuo.szcp.pojo.User;
import com.huahuo.szcp.service.CollectionService;
import com.huahuo.szcp.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

/**
 * @author Administrator
 * @description 针对表【transaction_record】的数据库操作Service实现
 * @createDate 2024-02-06 20:44:04
 */
@Service
public class TransactionRecordServiceImpl extends ServiceImpl<TransactionRecordMapper, TransactionRecord>
        implements TransactionRecordService {
    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionRecordMapper transactionRecordMapper;
    @Autowired
    private CollectionService collectionService;

    @Transactional
    public Result<String> addTransactionRecord(Integer productId, Integer buyerId, Integer sellerId) {
        // 查询商品的属性
        Collection collection = collectionMapper.selectById(productId);
        if (collection == null) {
            return Result.error(301, null, "商品不存在");
        }

        String productName = collection.getName();
        BigDecimal transactionAmount = collection.getPrice();

        // 查询买方和卖方的余额
        User buyer = userMapper.selectById(buyerId);
        User seller = userMapper.selectById(sellerId);

        if (buyer == null || seller == null) {
            return Result.error(301, null, "用户不存在");
        }

        BigDecimal buyerBalance = buyer.getBalance();
        BigDecimal sellerBalance = seller.getBalance();

        // 判断买方余额是否足够
        if (buyerBalance.compareTo(transactionAmount) < 0) {
            return Result.error(301, null, "买方余额不足");
        }

        // 处理余额，买方减少余额，卖方增加余额
        buyer.setBalance(buyerBalance.subtract(transactionAmount));
        seller.setBalance(sellerBalance.add(transactionAmount));

        // 更新买方和卖方的余额
        userMapper.updateById(buyer);
        userMapper.updateById(seller);

        // 生成固定长度有意义的交易单号
        String transactionNumber = generateTransactionNumber();

        // 插入新的交易记录
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setProductName(productName);
        transactionRecord.setTransactionTime(LocalDateTime.now());
        transactionRecord.setTransactionNumber(transactionNumber);
        transactionRecord.setBuyerId(buyerId);
        transactionRecord.setSellerId(sellerId);
        transactionRecord.setTransactionAmount(transactionAmount);

        int rows = transactionRecordMapper.insert(transactionRecord);

        if (rows > 0) {
            collection.setOwner(buyer.getId());
            collectionService.updateById(collection);
            return Result.success(transactionNumber);
        } else
            return Result.error(301, null, "交易记录插入失败");
    }

    // 生成固定长度有意义的交易单号的函数
    private String generateTransactionNumber() {
        int length = 16;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rand.nextInt(characters.length())));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sb.append(sdf.format(new Date()));
        return sb.toString();
    }
}




