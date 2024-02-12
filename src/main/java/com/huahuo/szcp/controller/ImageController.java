package com.huahuo.szcp.controller;

import com.huahuo.szcp.common.Result;
import com.huahuo.szcp.utils.BaiduStableDiffusionUtils;
import com.huahuo.szcp.utils.QiniuImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api("图片相关接口")
@RequestMapping("/api/v1/image")
public class ImageController {

    @PostMapping("/ai/animal")
    @ApiOperation("文生图测试")
    @CrossOrigin
    public Result<String> NewAIAnimal(@RequestParam String type, @RequestParam String like, @RequestParam String dislike) {
        StringBuilder sb = new StringBuilder();
        sb.append("生成一只").append(type).append("它的特征是：").append(like);
        String s = BaiduStableDiffusionUtils.callStableDiffusionXL(sb.toString(), dislike);
        return s.equals("error") ? Result.error(500, null, "内部错误") : Result.success(s);
    }

    @PostMapping("/ai/szcp/xs")
    @ApiOperation("文生图训练测试")
    @CrossOrigin
    public Result<String> szcp(@RequestParam String like, @RequestParam String dislike) {
        String s = BaiduStableDiffusionUtils.callStableDiffusionXL(like, dislike);
        return s.equals("error") ? Result.error(500, null, "内部错误") : Result.success(s);
    }

    @ApiOperation("上传图片")
    @PostMapping("/upload")
    @CrossOrigin
    public Result<String> uploadImage(@RequestParam MultipartFile file) {
        return Result.success(QiniuImageUtils.upload(file));
    }

    //  人物头像，64位像素风格，我希望他具新年的主题2024年，龙年，红色系，最好是个小女孩
    @PostMapping("/ai/head/prompt")
    @ApiOperation("文生头像 prompt")
    @CrossOrigin
    public Result<String> newHeadImg(@RequestParam String type, @RequestParam Integer quality, @RequestParam String theme, @RequestParam String color, @RequestParam String expression, @RequestParam String element) {
        String prompt = "生成一个" + type + "头像" + quality + "位像素风格的," + "具有" + theme + "主题,画面是" + color + "色系," + "表现的情绪应该是" + expression + ",我还希望加入" + element + "元素";
        String s = BaiduStableDiffusionUtils.callStableDiffusionXL(prompt, null);
        return s.equals("error") ? Result.error(500, null, "内部错误") : Result.success(s);
    }
}
