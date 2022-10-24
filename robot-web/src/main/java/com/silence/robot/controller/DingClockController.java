package com.silence.robot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.clock.DingClockService;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.clock.DingClockDto;
import com.silence.robot.dto.DataResponse;

@RestController
public class DingClockController {

    @Resource
    private DingClockService dingClockService;


    @GetMapping("/getDingClockList")
    public DataResponse<List<DingClockDto>> getDataDictList(@RequestParam Integer page, @RequestParam Integer limit) {
        RobotPage<DingClockDto> dataDictList = dingClockService.getDingClockList(page, limit);
        DataResponse<List<DingClockDto>> dataResponse = new DataResponse<>(dataDictList.getList());
        dataResponse.setCount(dataDictList.getTotal());
        return dataResponse;
    }

    @PostMapping("/addDingClock")
    public DataResponse<?> addDingClock(@RequestBody DingClockDto dingClockDto) {
        dingClockService.addDingClock(dingClockDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateDingClock")
    public DataResponse<?> updateDingClock(@RequestBody DingClockDto dingClockDto) {
        dingClockService.updateDingClock(dingClockDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteDingClock")
    public DataResponse<?> deleteDingClock(@RequestParam String id) {
        dingClockService.deleteDingClock(id);
        return new DataResponse<>();
    }

    @GetMapping("/dingClick")
    public DataResponse<?> dingClick(@RequestParam String id) {
        dingClockService.dingClick(id);
        return new DataResponse<>();
    }
}
