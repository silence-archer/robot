package com.silence.robot.thread;

import com.silence.robot.config.FtpConfig;
import com.silence.robot.domain.FileConfigDto;
import com.silence.robot.service.LogFileReadService;
import com.silence.robot.utils.FileUtils;
import com.silence.robot.utils.FtpUtils;
import com.silence.robot.utils.SpringContextHelper;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * 文件下载线程执行类
 *
 * @author silence
 * @date 2021/2/20
 */
public class FileDownloadThread implements Runnable{

    private final CountDownLatch countDownLatch;

    private final FileConfigDto fileConfigDto;

    private final LogFileReadService logFileReadService;

    public FileDownloadThread(CountDownLatch countDownLatch, FileConfigDto fileConfigDto) {
        this.countDownLatch = countDownLatch;
        this.fileConfigDto = fileConfigDto;
        this.logFileReadService = SpringContextHelper.getBean(LogFileReadService.class);
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
        if (FileUtils.exists(fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename())) {
            long remoteFileSize = FtpUtils.getFileSize(ftpConfig, fileConfigDto.getRemoteFilepath(), fileConfigDto.getFilename());
            File file = new File(fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename());
            long localFileSize = file.length();
            if (remoteFileSize == localFileSize) {
                countDownLatch.countDown();
                return;
            } else if (remoteFileSize > localFileSize) {
                FtpUtils.download(ftpConfig, fileConfigDto.getRemoteFilepath(), fileConfigDto.getFilename(), fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename());
                logFileReadService.read(fileConfigDto.getBusinessType(), fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename(), localFileSize, remoteFileSize);
            } else {
                FtpUtils.download(ftpConfig, fileConfigDto.getRemoteFilepath(), fileConfigDto.getFilename(), fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename());
                logFileReadService.read(fileConfigDto.getBusinessType(), fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename());
            }
        } else {
            FtpUtils.download(ftpConfig, fileConfigDto.getRemoteFilepath(), fileConfigDto.getFilename(), fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename());
            logFileReadService.read(fileConfigDto.getBusinessType(), fileConfigDto.getLocalFilepath(), fileConfigDto.getFilename());
        }
        countDownLatch.countDown();
    }

}
