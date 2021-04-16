package com.silence.robot.service;

import com.silence.robot.domain.LogFileDto;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 日志读取服务
 *
 * @author silence
 * @date 2021/2/5
 */
@Service
public class LogFileReadService {

    private final Logger logger = LoggerFactory.getLogger(LogFileReadService.class);

    @Resource
    private TLogFileMapper logFileMapper;
    /**
     * 日志文件读取
     * @author silence
     * @date 2021/4/10 1:30
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return java.util.List<com.silence.robot.domain.LogFileDto>
     */
    public void read(String filePath, String fileName) {
        File file = new File(filePath + File.separator + fileName);
        read(filePath, fileName, 0, file.length());
    }

    /**
     * 从文件指定位置开始读文件
     * @author silence
     * @date 2021/4/16 17:21
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param startPos 起始偏移量
     * @param endPos 终止偏移量
     */
    public void read(String filePath, String fileName, long startPos, long endPos) {
        List<String> lines = FileUtils.splitReadFile(startPos, endPos, new File(filePath + File.separator + fileName));
        StringBuilder context = new StringBuilder();
        List<LogFileDto> list = new ArrayList<>();
        for (String line : lines) {
            logger.info(line);
            if(CommonUtils.isEmpty(line)) {
                continue;
            }
            if ("2".equals(line.substring(0, 1)) && line.indexOf("[") == 24) {
                if (CommonUtils.isNotEmpty(list)) {
                    list.get(list.size()-1).setContext(context.toString());
                    if (list.size() > 1000) {
                        logFileMapper.insertAndBatch(BeanUtils.copyList(TLogFile.class, list, "id"));
                        list.clear();
                    }

                }
                LogFileDto logFileDto = new LogFileDto();
                context.delete(0, context.length());
                String dateTime = line.substring(0, 23);
                String serviceName = line.substring(line.indexOf("[")+1, line.indexOf("]"));
                String subLine = line.substring(line.indexOf("]")+2);
                String seqNo = subLine.substring(subLine.indexOf("[")+1, subLine.indexOf("]"));
                if (seqNo.split(",").length == 0) {
                    continue;
                }
                String traceId = seqNo.split(",")[3];
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
                String content = subLine.substring(subLine.indexOf(" ")+1).substring(subLine.indexOf(" ")+1);
                context.append(content);
                logFileDto.setDateTime(DateUtils.parseDateByString1(dateTime));
                logFileDto.setServiceName(serviceName);
                logFileDto.setLineNum(Integer.valueOf(lineNum));
                logFileDto.setLevel(level);
                logFileDto.setClassName(className);
                logFileDto.setThreadName(threadName);
                logFileDto.setTraceId(traceId);
                logFileDto.setTranCode(tranCode);
                list.add(logFileDto);
            }else {
                context.append("\n").append(line);
            }

        }
    }

}
