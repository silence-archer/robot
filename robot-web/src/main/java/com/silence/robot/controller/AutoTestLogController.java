package com.silence.robot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.AutoTestDto;
import com.silence.robot.domain.AutoTestLogDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.AutoTestLogService;
import com.silence.robot.service.AutoTestService;

@RestController
public class AutoTestLogController {

    @Resource
    private AutoTestLogService autoTestLogService;


    @GetMapping("/queryAutoTestLog")
    public DataResponse<List<AutoTestLogDto>> query(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam(required = false) String testCaseDesc) {
        RobotPage<AutoTestLogDto> robotPage = autoTestLogService.selectAllByPage(page, limit, testCaseDesc);
        DataResponse<List<AutoTestLogDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(response.getCount());
        return response;
    }


}
