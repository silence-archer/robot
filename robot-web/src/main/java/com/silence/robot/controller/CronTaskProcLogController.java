package com.silence.robot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.CronTaskInfo;
import com.silence.robot.domain.CronTaskProcLogDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.CronTaskProcLogService;

@RestController
public class CronTaskProcLogController {

    @Resource
    private CronTaskProcLogService cronTaskProcLogService;

    @GetMapping("/queryCronTaskProcLog")
    public DataResponse<List<CronTaskProcLogDto>> queryCronTaskProcLog(@RequestParam Integer page, @RequestParam Integer limit){
        RobotPage<CronTaskProcLogDto> robotPage = cronTaskProcLogService.queryCronTaskProcLog(page, limit);
        DataResponse<List<CronTaskProcLogDto>> dataResponse = new DataResponse<>(robotPage.getList());
        dataResponse.setCount(robotPage.getTotal());
        return dataResponse;
    }

    @GetMapping("/retryCronTask")
    public DataResponse<?> retryCronTask(@RequestParam String id){
        cronTaskProcLogService.retry(id);
        return new DataResponse<>();
    }
}
