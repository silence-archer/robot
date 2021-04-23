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
 * @date 2021/4/16
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
}
