package com.silence.robot.job.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.silence.robot.clock.SchedulingClickService;
import com.silence.robot.job.RobotQuartzTask;


@Component
public class SchedulingClickJob implements RobotQuartzTask {

    @Resource
    private SchedulingClickService schedulingClickService;

    @Override
    public void execute() {
        schedulingClickService.firstClick();
    }
}
