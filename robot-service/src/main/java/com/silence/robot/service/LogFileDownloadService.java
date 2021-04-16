package com.silence.robot.service;

import com.silence.robot.domain.FileConfigDto;
import com.silence.robot.thread.FileDownloadThread;
import com.silence.robot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 日志下载服务
 *
 * @author silence
 * @date 2021/2/5
 */
@Service
public class LogFileDownloadService {

    private final Logger logger = LoggerFactory.getLogger(LogFileDownloadService.class);

    public void download(List<FileConfigDto> fileConfigDtos) {
        CountDownLatch countDownLatch = new CountDownLatch(fileConfigDtos.size());
        fileConfigDtos.forEach(fileConfigDto -> CommonUtils.THREAD_POOL_EXECUTOR.execute(new FileDownloadThread(countDownLatch, fileConfigDto)));
        try {
            boolean await = countDownLatch.await(30, TimeUnit.MINUTES);
            if (!await) {
                throw new IllegalStateException("日志下载失败");
            }
        } catch (InterruptedException e) {
            logger.error("线程执行失败", e);
        }
    }
}
