package com.silence.robot.thread;

import com.silence.robot.config.FtpConfig;
import com.silence.robot.domain.FileConfigDto;
import com.silence.robot.utils.FtpUtils;

import java.util.concurrent.CountDownLatch;

/**
 * 文件下载线程执行类
 *
 * @author silence
 * @date 2021/2/20
 */
public class FileDownloadThread implements Runnable{

    private CountDownLatch countDownLatch;

    private FileConfigDto fileConfigDto;

    public FileDownloadThread(CountDownLatch countDownLatch, FileConfigDto fileConfigDto) {
        this.countDownLatch = countDownLatch;
        this.fileConfigDto = fileConfigDto;
    }

    @Override
    public void run() {
        FtpConfig ftpConfig = new FtpConfig();
        ftpConfig.setFtpType(fileConfigDto.getTransferType());
        ftpConfig.setHost(fileConfigDto.getRemoteIp());
        ftpConfig.setPort(fileConfigDto.getRemotePort());
        ftpConfig.setUsername(fileConfigDto.getRemoteUsername());
        ftpConfig.setPassword(fileConfigDto.getRemotePassword());
        ftpConfig.setSecretKey(fileConfigDto.getRemoteSecretKey());

        FtpUtils.download(ftpConfig, fileConfigDto.getRemoteFilepath(), fileConfigDto.getFilename(), fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename());
        countDownLatch.countDown();
    }
}
