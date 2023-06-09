package com.silence.robot.job.task;

import com.silence.robot.domain.FileConfigDto;
import com.silence.robot.job.RobotQuartzTask;
import com.silence.robot.service.FileConfigService;
import com.silence.robot.service.LogFileDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 日志文件下载
 *
 * @author silence
 * @since 2021/2/3
 */
@Component
public class LogFileDownloadJob implements RobotQuartzTask {

    private final Logger logger = LoggerFactory.getLogger(LogFileDownloadJob.class);

    @Resource
    private LogFileDownloadService logFileDownloadService;

    @Resource
    private FileConfigService fileConfigService;

    @Override
    public void execute() {
        List<FileConfigDto> fileConfigDtos = fileConfigService.queryAllFileConfig();
        Map<String, List<FileConfigDto>> collect = fileConfigDtos.stream().collect(Collectors.groupingBy(FileConfigDto::getBusinessType));
        collect.forEach((s, fileConfigSubDtos) -> {
            logger.info("开始进行[{}]环境的日志文件下载>>>>>>>>>>>", s);
            logFileDownloadService.download(fileConfigSubDtos);
        });

    }
}
