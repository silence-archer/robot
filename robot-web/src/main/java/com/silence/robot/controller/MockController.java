package com.silence.robot.controller;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.MockInfo;
import com.silence.robot.domain.MockRequestInfo;
import com.silence.robot.domain.MockResponseInfo;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.service.MockService;
import com.silence.robot.service.SubscribeConfigInfoService;
import com.silence.robot.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 挡板管理
 *
 * @author silence
 * @since 2020/10/7
 */
@RestController
public class MockController {

    private final Logger logger = LoggerFactory.getLogger(MockController.class);

    @Resource
    private MockService mockService;

    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;

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

    @PostMapping("/mock/bob")
    public MockResponseInfo mockBob(@RequestBody MockRequestInfo mockRequestInfo){
        return mockService.mockBob(mockRequestInfo);
    }

    @PostMapping("/mock/esb")
    public JSONObject mock(@RequestParam Map<String, Object> map){
        logger.info("当前请求入参："+map);
        String data = null;
        for (String s : map.keySet()) {
            data = s;
        }
        JSONObject request = JSONObject.parseObject(data);
        MockRequestInfo mockRequestInfo = new MockRequestInfo();
        assert request != null;
        mockRequestInfo.setRequest(request.getJSONObject("BODY"));
        mockRequestInfo.setModule("ESB");
        mockRequestInfo.setUri(request.getJSONObject("SYS_HEAD").getString("MESSAGE_TYPE")+request.getJSONObject("SYS_HEAD").getString("MESSAGE_CODE"));
        JSONObject response = mockService.mock(mockRequestInfo);
        if (response.isEmpty()) {
            String uri = subscribeConfigInfoService.getConfigValue(ConfigEnum.ESB_URI_ENUM);
            response = HttpUtils.doPost(uri, data);
        }
        logger.info("响应信息为：{}", response);
        return response;
    }
}
