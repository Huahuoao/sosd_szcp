package com.huahuo.szcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huahuo.szcp.mapper.GenerateImageRecordsMapper;
import com.huahuo.szcp.pojo.GenerateImageRecords;
import com.huahuo.szcp.service.GenerateImageRecordsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @description 针对表【generate_image_records】的数据库操作Service实现
 * @createDate 2024-02-15 17:07:19
 */
@Service
public class GenerateImageRecordsServiceImpl extends ServiceImpl<GenerateImageRecordsMapper, GenerateImageRecords>
        implements GenerateImageRecordsService {
    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    @Override
    public void saveRecord(String target, String url) {
        Runnable task = () -> {
            GenerateImageRecords generateImageRecords = new GenerateImageRecords();
            generateImageRecords.setTarget(target);
            generateImageRecords.setUrl(url);
            generateImageRecords.setUpdateAt(LocalDateTime.now());
            generateImageRecords.setCreateAt(LocalDateTime.now());
            save(generateImageRecords);
        };
        threadPool.submit(task);
    }
}




