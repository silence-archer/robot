/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CronTaskService
 * Author:   silence
 * Date:     2019/12/27 14:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.CronTaskInfo;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.clock.DingClockDto;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TCronTaskMapper;
import com.silence.robot.model.TCronTask;
import com.silence.robot.model.TDingClockInfo;
import com.silence.robot.schedule.TimerJobSchedule;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @since 2019/12/27
 * 
 */
@Service
public class CronTaskService {

    private final Logger logger = LoggerFactory.getLogger(CronTaskService.class);

    @Resource
    private TCronTaskMapper cronTaskMapper;

    @Resource
    private TimerJobSchedule timerJobSchedule;

    public void addCronTask(CronTaskInfo cronTaskInfo){
        TCronTask cronTask = new TCronTask();
        TCronTask task = cronTaskMapper.selectByJobName(cronTaskInfo.getJobName());
        if(task != null){
            throw new BusinessException(ExceptionCode.EXIST_ERROR);
        }
        cronTask.setCronExpr(CommonUtils.checkCronExpr(cronTaskInfo.getCronExpr()));
        cronTask.setEffectFlag(cronTaskInfo.getEffectFlag());
        cronTask.setJobClass(cronTaskInfo.getJobClass());
        cronTask.setJobName(cronTaskInfo.getJobName());
        cronTask.setJobDesc(cronTaskInfo.getJobDesc());
        cronTaskMapper.insert(cronTask);
        if("Y".equals(cronTaskInfo.getEffectFlag())){
            timerJobSchedule.start(cronTask.getJobName(), cronTask.getCronExpr(), cronTask.getJobClass(), cronTask.getJobDesc());
        }
    }

    public void updateCronTask(CronTaskInfo cronTaskInfo){
        TCronTask task = cronTaskMapper.selectByJobName(cronTaskInfo.getJobName());
        if(task == null){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        task.setJobDesc(cronTaskInfo.getJobDesc());
        task.setJobClass(cronTaskInfo.getJobClass());
        task.setEffectFlag(cronTaskInfo.getEffectFlag());
        task.setCronExpr(CommonUtils.checkCronExpr(cronTaskInfo.getCronExpr()));
        cronTaskMapper.updateByPrimaryKey(task);
        timerJobSchedule.stop(task.getJobName());
        if("Y".equals(task.getEffectFlag())){
            timerJobSchedule.start(task.getJobName(), task.getCronExpr(), task.getJobClass(), task.getJobDesc());
        }else{
            timerJobSchedule.stop(task.getJobName());
        }
    }

    public void deleteCronTask(String jobName){
        TCronTask task = cronTaskMapper.selectByJobName(jobName);
        if(task == null){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        cronTaskMapper.deleteByPrimaryKey(task.getId());
        timerJobSchedule.stop(task.getJobName());

    }


    public RobotPage<CronTaskInfo> queryCronTask(Integer page, Integer limit){
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TCronTask> cronTasks = cronTaskMapper.selectAll();
        PageInfo<TCronTask> pageInfo = new PageInfo<>(cronTasks);
        List<CronTaskInfo> cronTaskInfos = BeanUtils.copyList(CronTaskInfo.class, cronTasks);
        return new RobotPage<>(pageInfo.getTotal(), cronTaskInfos);
    }
}