package com.silence.robot.job.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.silence.robot.domain.freemarker.CommemoratingFreeMarkerDto;
import com.silence.robot.job.RobotQuartzTask;
import com.silence.robot.service.CommemoratingInfoService;

@Service
public class CommemoratingJob implements RobotQuartzTask {

    @Resource
    private CommemoratingInfoService commemoratingInfoService;

    @Override
    public void execute() {
        CommemoratingFreeMarkerDto commemoratingFreeMarkerDto = commemoratingInfoService.generateDto();
        if (commemoratingFreeMarkerDto == null) {
            return;
        }
        commemoratingInfoService.generate(commemoratingFreeMarkerDto);
        commemoratingInfoService.send(commemoratingFreeMarkerDto);
    }
}
