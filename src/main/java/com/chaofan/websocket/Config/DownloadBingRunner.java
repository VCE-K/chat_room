package com.chaofan.websocket.Config;

import com.chaofan.websocket.Util.BingImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Chaofan at 2019/2/14 9:31
 * email:chaofan2685@qq.com
 * 该类用于项目启动时下载Bing壁纸
 **/
@Component
public class DownloadBingRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadBingRunner.class);

    @Override
    public void run(String... args) throws Exception {
        Integer sum = BingImageUtil.download(0,7);
        sum += BingImageUtil.download(7,7);
        LOGGER.debug("本次同步了"+sum+"张壁纸！");
    }

}
