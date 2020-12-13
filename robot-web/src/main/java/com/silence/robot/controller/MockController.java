package com.silence.robot.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.MockInfo;
import com.silence.robot.domain.MockRequestInfo;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.MockService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 挡板管理
 *
 * @author silence
 * @date 2020/10/7
 */
@RestController
public class MockController {

    @Resource
    private MockService mockService;

    @GetMapping("/getMockInfo")
    public DataResponse<List<MockInfo>> getMockInfo(@RequestParam Integer page, @RequestParam Integer limit) {

        RobotPage<MockInfo> mockInfoByPage = mockService.getMockInfoByPage(page, limit);

        DataResponse<List<MockInfo>> dataResponse = new DataResponse<>(mockInfoByPage.getList());
        dataResponse.setCount(mockInfoByPage.getTotal());
        return dataResponse;
    }

    @GetMapping("/getMockInfoByCondition")
    public DataResponse<List<MockInfo>> getMockInfoByCondition(@RequestParam(required = false) String mockName, @RequestParam(required = false) String mockInput, @RequestParam(required = false) String mockUrl, @RequestParam(required = false) String mockModule, @RequestParam Integer page, @RequestParam Integer limit) {

        RobotPage<MockInfo> mockInfoByPage = mockService.getMockInfoByCondition(mockName, mockInput, mockUrl, mockModule, page, limit);

        DataResponse<List<MockInfo>> dataResponse = new DataResponse<>(mockInfoByPage.getList());
        dataResponse.setCount(mockInfoByPage.getTotal());
        return dataResponse;
    }

    @PostMapping("/addMock")
    public DataResponse<?> addMock(@RequestBody MockInfo mockInfo){
        mockService.addMock(mockInfo);
        return new DataResponse<>();
    }

    @PostMapping("/updateMock")
    public DataResponse<?> updateMock(@RequestBody MockInfo mockInfo){
        mockService.updateMock(mockInfo);
        return new DataResponse<>();
    }

    @GetMapping("/deleteMock")
    public DataResponse<?> deleteMock(@RequestParam String id){
        mockService.deleteMock(id);
        return new DataResponse<>();
    }

    @PostMapping("/mock")
    public JSONObject mock(@RequestBody MockRequestInfo mockRequestInfo){
        return mockService.mock(mockRequestInfo);
    }
}
