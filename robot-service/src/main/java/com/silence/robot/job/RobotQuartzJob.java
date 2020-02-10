/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AccessTokenJob
 * Author:   silence
 * Date:     2019/12/26 16:40
 * Description: access token 定时获取
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.job;

import com.silence.robot.mapper.TCronTaskProcLogMapper;
import com.silence.robot.model.TCronTaskProcLog;
import com.silence.robot.schedule.TimerJobSchedule;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.SpringContextHelper;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈access token 定时获取〉
 *
 * @author silence
 * @create 2019/12/26
 * @since 1.0.0
 */
public class RobotQuartzJob extends MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob {

    private final Logger logger = LoggerFactory.getLogger(RobotQuartzJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context)  {
        JobDetail jobDetail = context.getJobDetail();
        logger.info("定时任务{},{}开始执行", jobDetail.getKey(), context.getFireTime());
        String jobClass = jobDetail.getJobDataMap().getString("jobClass");
        RobotQuartzTask quartzTask = SpringContextHelper.getBean(jobClass, RobotQuartzTask.class);
        logger.info("获取任务执行类{}，任务描述为{}",quartzTask.getClass(),jobDetail.getJobDataMap().getString("jobDesc"));
        TCronTaskProcLogMapper cronTaskProcLogMapper = SpringContextHelper.getBean(TCronTaskProcLogMapper.class);
        TCronTaskProcLog cronTaskProcLog = cronTaskProcLogMapper.selectByJobName(jobDetail.getKey().getName());
        if(cronTaskProcLog == null){
            cronTaskProcLog = new TCronTaskProcLog();
            cronTaskProcLog.setId(CommonUtils.getUuid());
            cronTaskProcLog.setJobName(jobDetail.getKey().getName());
            cronTaskProcLog.setJobDesc(jobDetail.getJobDataMap().getString("jobDesc"));


        }
        try {
            quartzTask.execute();
            cronTaskProcLog.setProcStatus("SUCCESS");
            cronTaskProcLog.setErrorMsg("");
        }catch (Exception e){
            logger.error("定时任务{}，执行出现异常",jobDetail.getKey(),e);
            TimerJobSchedule timerJobSchedule = SpringContextHelper.getBean(TimerJobSchedule.class);
            timerJobSchedule.stop(jobDetail.getKey().getName());
            cronTaskProcLog.setProcStatus("FAIL");
            String message = e.getMessage().length() > 200 ? e.getMessage().substring(0,200) : e.getMessage();
            cronTaskProcLog.setErrorMsg(message);
        }finally {
            cronTaskProcLog.setUpdateTime(new Date());
            if(cronTaskProcLog.getCreateTime() == null){
                cronTaskProcLog.setCreateTime(new Date());
                cronTaskProcLogMapper.insert(cronTaskProcLog);
            }else {
                cronTaskProcLogMapper.updateByPrimaryKey(cronTaskProcLog);
            }
        }


    }
}