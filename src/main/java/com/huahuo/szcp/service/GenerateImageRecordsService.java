package com.huahuo.szcp.service;

import com.huahuo.szcp.pojo.GenerateImageRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Administrator
 * @description 针对表【generate_image_records】的数据库操作Service
 * @createDate 2024-02-15 17:07:19
 */
public interface GenerateImageRecordsService extends IService<GenerateImageRecords> {
    public void saveRecord(String target,String url);
}
