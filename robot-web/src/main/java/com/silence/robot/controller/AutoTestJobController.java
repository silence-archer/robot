package com.silence.robot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.AutoTestJobDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.AutoTestJobService;
import com.silence.robot.utils.HttpUtils;
import com.silence.robot.utils.TraceUtils;

@RestController
public class AutoTestJobController {

    @Resource
    private AutoTestJobService autoTestJobService;
    @PostMapping("/addAutoTestJob")
    public DataResponse<?> add(@RequestBody AutoTestJobDto autoTestJobDto) {
        autoTestJobService.add(autoTestJobDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateAutoTestJob")
    public DataResponse<?> update(@RequestBody AutoTestJobDto autoTestJobDto) {
        autoTestJobService.update(autoTestJobDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteAutoTestJob")
    public DataResponse<?> delete(@RequestParam String id) {
        autoTestJobService.delete(id);
        return new DataResponse<>();
    }

    @GetMapping("/deleteBatchAutoTestJob")
    public DataResponse<?> deleteBatch(@RequestParam List<String> ids) {
        autoTestJobService.deleteBatch(ids);
        return new DataResponse<>();
    }

    @GetMapping("/queryAutoTestJob")
    public DataResponse<List<AutoTestJobDto>> query(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam(required = false) String jobDesc) {
        RobotPage<AutoTestJobDto> robotPage = autoTestJobService.selectAllByPage(page, limit, jobDesc);
        DataResponse<List<AutoTestJobDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(response.getCount());
        return response;
    }

    @GetMapping("/getAllAutoTestJob")
    public DataResponse<List<AutoTestJobDto>> getAllAutoTestJob() {
        return new DataResponse<>(autoTestJobService.selectAll());
    }

    @GetMapping("/runAutoTestJob")
    public DataResponse<?> run(@RequestParam String jobName) {
        TraceUtils.setParentLoginUsername(HttpUtils.getLoginUserName());
        autoTestJobService.run(jobName);
        return new DataResponse<>();
    }


}
