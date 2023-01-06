package com.silence.robot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.AutoTestDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.AutoTestService;
import com.silence.robot.utils.HttpUtils;
import com.silence.robot.utils.TraceUtils;

@RestController
public class AutoTestController {

    @Resource
    private AutoTestService autoTestService;
    @PostMapping("/addAutoTest")
    public DataResponse<?> add(@RequestBody AutoTestDto autoTestDto) {
        autoTestService.add(autoTestDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateAutoTest")
    public DataResponse<?> update(@RequestBody AutoTestDto autoTestDto) {
        autoTestService.update(autoTestDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteAutoTest")
    public DataResponse<?> delete(@RequestParam String id) {
        autoTestService.delete(id);
        return new DataResponse<>();
    }

    @GetMapping("/deleteBatchAutoTest")
    public DataResponse<?> deleteBatch(@RequestParam List<String> ids) {
        autoTestService.deleteBatch(ids);
        return new DataResponse<>();
    }

    @GetMapping("/queryAutoTest")
    public DataResponse<List<AutoTestDto>> query(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam(required = false) String testCaseDesc) {
        RobotPage<AutoTestDto> robotPage = autoTestService.selectAllByPage(page, limit, testCaseDesc);
        DataResponse<List<AutoTestDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(response.getCount());
        return response;
    }

    @GetMapping("/runAutoTest")
    public DataResponse<?> run(@RequestParam String testCaseName) {
        TraceUtils.setParentLoginUsername(HttpUtils.getLoginUserName());
        autoTestService.run(testCaseName);
        return new DataResponse<>();
    }


}
