package com.huahuo.szcp.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {
    public static void saveBase64Image(String base64Image) {
        try {
            // 解码Base64图像数据
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            // 保存图像到指定路径
            FileOutputStream fos = new FileOutputStream("E:\\工作\\file\\1.jpg");
            fos.write(imageBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save image.");
        }
    }

}
