package com.silence.robot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.AutoTestLogDetailDto;
import com.silence.robot.domain.AutoTestLogDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.AutoTestLogDetailService;
import com.silence.robot.service.AutoTestLogService;

@RestController
public class AutoTestLogDetailController {

    @Resource
    private AutoTestLogDetailService autoTestLogDetailService;


    @GetMapping("/queryAutoTestLogDetail")
    public DataResponse<List<AutoTestLogDetailDto>> query(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String batchNo) {
        RobotPage<AutoTestLogDetailDto> robotPage = autoTestLogDetailService.selectAllByPage(page, limit, batchNo);
        DataResponse<List<AutoTestLogDetailDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(response.getCount());
        return response;
    }


}
