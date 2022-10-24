package com.silence.robot.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.CronTaskInfo;
import com.silence.robot.domain.CronTaskProcLogDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TCronTaskProcLogMapper;
import com.silence.robot.model.TCronTask;
import com.silence.robot.model.TCronTaskProcLog;
import com.silence.robot.schedule.TimerJobSchedule;
import com.silence.robot.utils.BeanUtils;

@Service
public class CronTaskProcLogService {
    @Resource
    private TimerJobSchedule timerJobSchedule;

    @Resource
    private TCronTaskProcLogMapper cronTaskProcLogMapper;

    public RobotPage<CronTaskProcLogDto> queryCronTaskProcLog(Integer page, Integer limit){
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TCronTaskProcLog> cronTaskProcLogs = cronTaskProcLogMapper.selectAll();
        PageInfo<TCronTaskProcLog> pageInfo = new PageInfo<>(cronTaskProcLogs);
        List<CronTaskProcLogDto> cronTaskProcLogDtos = BeanUtils.copyList(CronTaskProcLogDto.class, cronTaskProcLogs);
        return new RobotPage<>(pageInfo.getTotal(), cronTaskProcLogDtos);
    }

    public void retry(String id) {
        TCronTaskProcLog cronTaskProcLog = cronTaskProcLogMapper.selectByPrimaryKey(id);
        if ("SUCCESS".equals(cronTaskProcLog.getProcStatus())) {
            throw new BusinessException(ExceptionCode.SCHEDULER_RESTART_CHECK_ERROR);
        }
        timerJobSchedule.restart(cronTaskProcLog.getJobName());
    }
}
