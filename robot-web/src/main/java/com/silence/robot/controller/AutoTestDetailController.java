package com.silence.robot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.AutoTestDetailDto;
import com.silence.robot.domain.AutoTestDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.AutoTestDetailService;
import com.silence.robot.service.AutoTestService;

@RestController
public class AutoTestDetailController {

    @Resource
    private AutoTestDetailService autoTestDetailService;
    @PostMapping("/addAutoTestDetail")
    public DataResponse<?> add(@RequestBody AutoTestDetailDto autoTestDetailDto) {
        autoTestDetailService.add(autoTestDetailDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateAutoTestDetail")
    public DataResponse<?> update(@RequestBody AutoTestDetailDto autoTestDetailDto) {
        autoTestDetailService.update(autoTestDetailDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteAutoTestDetail")
    public DataResponse<?> delete(@RequestParam String id) {
        autoTestDetailService.delete(id);
        return new DataResponse<>();
    }

    @GetMapping("/deleteBatchAutoTestDetail")
    public DataResponse<?> deleteBatch(@RequestParam List<String> ids) {
        autoTestDetailService.deleteBatch(ids);
        return new DataResponse<>();
    }

    @GetMapping("/queryAutoTestDetail")
    public DataResponse<List<AutoTestDetailDto>> query(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String testCaseName) {
        RobotPage<AutoTestDetailDto> robotPage = autoTestDetailService.selectAllByPage(page, limit, testCaseName);
        DataResponse<List<AutoTestDetailDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }


}
