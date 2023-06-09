package com.silence.robot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.LogFileDto;
import com.silence.robot.domain.QueryLogFileDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.mapper.TLogFileMapper;
import com.silence.robot.model.TLogFile;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 日志文件service
 *
 * @author silence
 * @since 2021/4/16
 */
@Service
public class LogFileService {

    @Resource
    private TLogFileMapper logFileMapper;

    public RobotPage<LogFileDto> getLogFileByCondition(QueryLogFileDto logFileDto, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Map<String, Object> param = BeanUtils.beanToMap(logFileDto);
        param.put("startDate", DateUtils.parseDateByString2(logFileDto.getStartDate()));
        param.put("endDate", DateUtils.parseDateByString2(logFileDto.getEndDate()));
        List<TLogFile> logFiles = logFileMapper.selectByCondition(param);
        PageInfo<TLogFile> pageInfo = new PageInfo<>(logFiles);
        List<LogFileDto> logFileDtos = BeanUtils.copyList(LogFileDto.class, logFiles);
        return new RobotPage<>(pageInfo.getTotal(), logFileDtos);
    }

    public void deleteLogFileByCondition(QueryLogFileDto logFileDto) {
        Map<String, Object> param = BeanUtils.beanToMap(logFileDto);
        param.put("startDate", DateUtils.parseDateByString2(logFileDto.getStartDate()));
        param.put("endDate", DateUtils.parseDateByString2(logFileDto.getEndDate()));
        logFileMapper.deleteByCondition(param);
    }

    public void newTxDeleteLogFile(QueryLogFileDto logFileDto) {
        deleteLogFileByCondition(logFileDto);
    }

    public void newTxInsertAndBatch(List<LogFileDto> list) {
        logFileMapper.insertAndBatch(BeanUtils.copyList(TLogFile.class, list, "id"));
    }

    public void insert(LogFileDto logFileDto) {
        logFileMapper.insert(BeanUtils.copy(TLogFile.class, logFileDto));
    }

    public LogFileDto getLogFileById(String id) {
        TLogFile logFile = logFileMapper.selectByPrimaryKey(id);
        return BeanUtils.copy(LogFileDto.class, logFile);
    }

    public void deleteLogFileById(String id) {
        logFileMapper.deleteByPrimaryKey(id);
    }

    public void deleteLogFiles(List<String> ids) {
        logFileMapper.deleteAndBatch(ids);
    }

    public void updateLogFile(LogFileDto logFileDto) {
        logFileMapper.updateByPrimaryKey(BeanUtils.copy(TLogFile.class, logFileDto));
    }

    public List<LogFileDto> getLogFileByBusinessType(String businessType) {

        List<TLogFile> logFiles = logFileMapper.selectByBusinessType(businessType);
        return BeanUtils.copyList(LogFileDto.class, logFiles);
    }

}
