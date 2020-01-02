/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TimerJobSchedule
 * Author:   silence
 * Date:     2019/12/24 18:07
 * Description: 定时任务调度
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.schedule;

import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.job.RobotQuartzJob;
import com.silence.robot.mapper.TCronTaskMapper;
import com.silence.robot.model.TCronTask;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈定时任务调度〉
 *
 * @author silence
 * @create 2019/12/24
 * @since 1.0.0
 */
@Component
public class TimerJobSchedule {

    private final Logger logger = LoggerFactory.getLogger(TimerJobSchedule.class);

    @Resource
    private TCronTaskMapper cronTaskMapper;

    @Resource
    private Scheduler scheduler;

    @PostConstruct
    public void load() {

        List<TCronTask> cronTasks = cronTaskMapper.selectAll();
        cronTasks.forEach(cronTask -> {
            if("Y".equals(cronTask.getEffectFlag())){
                start(cronTask.getJobName(), cronTask.getCronExpr(), cronTask.getJobClass(), cronTask.getJobDesc());
            }
        });


    }

    public void start(String jobName, String cronExpr, String jobClass, String jobDesc){
        JobDetailImpl jobDetail = new JobDetailImpl();
        JobKey jobKey = new JobKey(jobName);
        jobDetail.setKey(jobKey);
        CronTriggerImpl trigger = new CronTriggerImpl();
        TriggerKey triggerKey = new TriggerKey(jobName);
        trigger.setKey(triggerKey);
        jobDetail.setJobClass(RobotQuartzJob.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobClass",jobClass);
        jobDataMap.put("jobDesc",jobDesc);
        jobDetail.setJobDataMap(jobDataMap);
        try {

            if(scheduler.checkExists(jobKey)){
                logger.info("该定时任务{}已存在quartz调度中，无需再启动",jobName);
            }else{
                trigger.setCronExpression(cronExpr);
                scheduler.scheduleJob(jobDetail, trigger);
                logger.info("定时任务{}启动成功",jobName);
            }
            if(!scheduler.isStarted()){
                scheduler.start();
            }

        } catch (Exception e) {
            logger.error("定时任务{}，启动失败",jobName, e);
            throw new BusinessException(ExceptionCode.SCHEDULER_START_ERROR);
        }
    }

    public void stop(String jobName){
        try {
            JobKey jobKey = new JobKey(jobName);
            if(scheduler.checkExists(jobKey)){
                scheduler.deleteJob(new JobKey(jobName));
                logger.info("定时任务{}停止成功",jobName);
            }else{
                logger.info("该定时任务{}不存在quartz调度中，无需再停止",jobName);
            }


        } catch (SchedulerException e) {
            logger.error("定时任务{},停止失败",jobName, e);
            throw new BusinessException(ExceptionCode.SCHEDULER_STOP_ERROR);
        }
    }

    public void restart(String jobName){
        TCronTask cronTask = cronTaskMapper.selectByJobName(jobName);
        TriggerKey triggerKey = new TriggerKey(jobName);
        try {
            CronTriggerImpl newTrigger = new CronTriggerImpl();
            newTrigger.setKey(triggerKey);
            newTrigger.setCronExpression(cronTask.getCronExpr());
            scheduler.rescheduleJob(triggerKey, newTrigger);
            logger.error("定时任务{},重启成功",jobName);
        } catch (Exception e) {
            logger.error("定时任务{},重启失败",jobName, e);
            throw new BusinessException(ExceptionCode.SCHEDULER_RESTART_ERROR);
        }

    }

}