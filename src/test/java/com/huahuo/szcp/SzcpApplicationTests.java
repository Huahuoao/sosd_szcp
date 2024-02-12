package com.huahuo.szcp;

import com.huahuo.szcp.utils.BaiduStableDiffusionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SzcpApplicationTests {

    @Test
    void contextLoads() {
        BaiduStableDiffusionUtils.callStableDiffusionXL("像素风格大头照可爱小猫","不要太细腻");
    }

}
