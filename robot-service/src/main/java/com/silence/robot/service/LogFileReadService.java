package com.silence.robot.service;

import com.silence.robot.domain.LogFileDto;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.mapper.TLogFileMapper;
import com.silence.robot.model.TLogFile;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.DateUtils;
import com.silence.robot.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 日志读取服务
 *
 * @author silence
 * @since 2021/2/5
 */
@Service
public class LogFileReadService {

    private final Logger logger = LoggerFactory.getLogger(LogFileReadService.class);

    @Resource
    private TLogFileMapper logFileMapper;

    @Resource
    private LogFileService logFileService;
    /**
     * 日志文件读取
     * @author silence
     * @since 2021/4/10 1:30
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return java.util.List<com.silence.robot.domain.LogFileDto>
     */
    public void read(String businessType, String filePath, String fileName) {
        StringBuilder content = new StringBuilder();
        List<LogFileDto> list = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(Paths.get(filePath + File.separator + fileName));
            lines.forEach(line -> {
                try {
                    handleLogLine(businessType, content, list, line);
                } catch (Exception e) {
                    logger.error("当前行>>>[{}]解析失败！", line, e);
                    throw new BusinessException("日志文件解析失败", e);
                }

            });
        } catch (IOException e) {
            logger.error("读取文件失败", e);
        }

    }

    private void handleLogLine(String businessType, StringBuilder content, List<LogFileDto> list, String line) {
        if(CommonUtils.isNotEmpty(line)) {
            if ("2".equals(line.substring(0, 1)) && line.indexOf("[") == 24) {
                if (CommonUtils.isNotEmpty(list)) {
                    list.get(list.size()-1).setContent(content.toString());
                    if (list.size() > 100) {
                        synchronized (this) {
                            logFileMapper.insertAndBatch(BeanUtils.copyList(TLogFile.class, list, "id"));
                        }
                        list.clear();
                    }

                }
                LogFileDto logFileDto = new LogFileDto();
                content.delete(0, content.length());
                String dateTime = line.substring(0, 23);
                String serviceName = line.substring(line.indexOf("[")+1, line.indexOf("]"));
                String subLine = line.substring(line.indexOf("]")+2);
                String seqNo = subLine.substring(subLine.indexOf("[")+1, subLine.indexOf("]"));
                if (seqNo.split(",").length > 0) {
                    String traceId = seqNo.split(",")[0];
                    String subTraceId = null;
                    if(seqNo.split(",").length >1) {
                        subTraceId = seqNo.split(",")[1];
                    }
                    subLine = subLine.substring(subLine.indexOf("]")+2);
                    String tranCode = subLine.substring(1, subLine.indexOf("]"));
                    subLine = subLine.substring(subLine.indexOf("]")+2);
                    String threadName = subLine.substring(1, subLine.indexOf("]"));
                    subLine = subLine.substring(subLine.indexOf("]")+2);
                    String level = subLine.substring(0, subLine.indexOf(" "));
                    if (level.length() == 4) {
                        subLine = subLine.substring(subLine.indexOf(" ")+2);
                    }else {
                        subLine = subLine.substring(subLine.indexOf(" ")+1);
                    }
                    String className = subLine.substring(0, subLine.indexOf(" "));
                    subLine = subLine.substring(subLine.indexOf(" ")+1);
                    String lineNum = subLine.substring(0, subLine.indexOf(" "));
                    String contentStr = subLine.substring(subLine.indexOf(" ")+3);
                    content.append(contentStr);
                    logFileDto.setDateTime(DateUtils.parseDateByString1(dateTime));
                    logFileDto.setServiceName(serviceName);
                    logFileDto.setLineNum(Integer.valueOf(lineNum));
                    logFileDto.setLevel(level);
                    logFileDto.setClassName(className);
                    logFileDto.setThreadName(threadName);
                    logFileDto.setTraceId(traceId);
                    logFileDto.setSubTraceId(subTraceId);
                    logFileDto.setTranCode(tranCode);
                    logFileDto.setBusinessType(businessType);
                    list.add(logFileDto);
                }
            }else {
                content.append("\n").append(line);
            }
        }
    }

    /**
     * 从文件指定位置开始读文件
     * @author silence
     * @since 2021/4/16 17:21
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param startPos 起始偏移量
     * @param endPos 终止偏移量
     */
    public void read(String businessType, String filePath, String fileName, long startPos, long endPos) {
        List<String> lines = FileUtils.splitReadFile(startPos, endPos, new File(filePath + File.separator + fileName));
        StringBuilder content = new StringBuilder();
        List<LogFileDto> list = new ArrayList<>();
        lines.forEach(line -> {
            try {
                handleLogLine(businessType, content, list, line);
            } catch (Exception e) {
                logger.error("当前行>>>[{}]解析失败！", line, e);
                throw new BusinessException("日志文件解析失败", e);
            }
        });
    }

    public synchronized void insertAndBatchSplit(List<LogFileDto> list, int chunkSize) {
        int size = list.size() / chunkSize;
        for (int i = 0; i <= size; i++) {
            List<LogFileDto> logFileDtos = list.subList(i * chunkSize, Math.min(i * chunkSize + chunkSize, list.size()));
            if(CommonUtils.isNotEmpty(logFileDtos)){
                logFileService.newTxInsertAndBatch(logFileDtos);
            }
        }
    }

}
