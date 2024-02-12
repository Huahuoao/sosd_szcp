package com.huahuo.szcp.utils;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaiduStableDiffusionUtils {


    private static final String apiKey = "PI79nMwZdYICRKMVBUnfs0Wl";
    private static final String secretKey = "hsAU8mZnlVnvF6sHT0UsxoKXB4d6OG9K";


    public static String getAccessToken() {
        String accessToken = null;
        try {
            String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + secretKey;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 解析返回的 JSON 数据获取 access_token
            JSONObject jsonResponse = JSONObject.parseObject(response.toString());
            accessToken = jsonResponse.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static String callStableDiffusionXL(String prompt, String negativePrompt) {
        try {
            String accessToken = getAccessToken();
            String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/text2image/sd_xl?access_token=" + accessToken;
            String size = "1024x1024";
            int steps = 20;
            int n = 2;
            String samplerIndex = "DPM++ SDE Karras";
            String requestBody = String.format("{\"prompt\": \"%s\", \"negative_prompt\": \"%s\", \"size\": \"%s\", \"steps\": %d, \"n\": %d, \"sampler_index\": \"%s\"}", prompt, negativePrompt, size, steps, n, samplerIndex);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(requestBody);
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String jsonResponse = response.toString();
            JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            JSONObject imageData = dataArray.getJSONObject(0);
            String base64Image = imageData.getString("b64_image");
            return QiniuImageUtils.upload(base64Image);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

}
