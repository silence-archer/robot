/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CronTaskController
 * Author:   silence
 * Date:     2019/12/27 14:28
 * Description: 定时任务参数配置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.controller;

import com.silence.robot.domain.CronTaskInfo;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.clock.DingClockDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.CronTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈定时任务参数配置〉
 *
 * @author silence
 * @create 2019/12/27
 * @since 1.0.0
 */
@RestController
public class CronTaskController {

    @Resource
    private CronTaskService cronTaskService;

    @PostMapping("/addCronTask")
    @RequiresPermissions("CRON:ADD")
    public DataResponse<?> addCronTask(@RequestBody CronTaskInfo cronTaskInfo){
        cronTaskService.addCronTask(cronTaskInfo);
        return new DataResponse<>();
    }
    @PostMapping("/updateCronTask")
    @RequiresPermissions("CRON:UPDATE")
    public DataResponse<?> updateCronTask(@RequestBody CronTaskInfo cronTaskInfo){
        cronTaskService.updateCronTask(cronTaskInfo);
        return new DataResponse<>();
    }
    @GetMapping("/deleteCronTask")
    @RequiresPermissions("CRON:DELETE")
    public DataResponse<?> deleteCronTask(@RequestParam String jobName){
        cronTaskService.deleteCronTask(jobName);
        return new DataResponse<>();
    }

    @GetMapping("/queryCronTask")
    public DataResponse<List<CronTaskInfo>> queryCronTask(@RequestParam Integer page, @RequestParam Integer limit){
        RobotPage<CronTaskInfo> taskInfos = cronTaskService.queryCronTask(page, limit);
        DataResponse<List<CronTaskInfo>> dataResponse = new DataResponse<>(taskInfos.getList());
        dataResponse.setCount(taskInfos.getTotal());
        return dataResponse;
    }




}