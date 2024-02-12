package com.huahuo.szcp.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class QiniuImageUtils {
    public static String upload(String base64Image) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "isGLzqxQMhq8JZBA7ln7KaKiTPIgMPNoDb6d2iqg";
        String secretKey = "Uq_ZqhSwNOaW7sbgKDYrWLSa3rpJGEbTNmCYzfH5";
        String bucket = "huahuoaoao";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "szcp-"+UUID.randomUUID().toString() + ".jpg";
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64Image));
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "https://images.fzuhuahuo.cn/" + putRet.key;
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);
                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }

    public static String upload(MultipartFile file) {
        try {
            //构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.region2());
            cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
            String accessKey = "isGLzqxQMhq8JZBA7ln7KaKiTPIgMPNoDb6d2iqg";
            String secretKey = "Uq_ZqhSwNOaW7sbgKDYrWLSa3rpJGEbTNmCYzfH5";
            String bucket = "huahuoaoao";
//默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = "szcp-"+UUID.randomUUID().toString() + ".jpg";
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(file.getBytes());
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return "https://images.fzuhuahuo.cn/" + putRet.key;
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);
                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
