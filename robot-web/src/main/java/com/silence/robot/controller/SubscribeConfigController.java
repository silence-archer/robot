package com.silence.robot.controller;

import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.SubscribeConfigDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.SubscribeConfigInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/10
 */
@RestController
public class SubscribeConfigController {
    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;

    @GetMapping("/getSubscribeConfig")
    public DataResponse<List<SubscribeConfigDto>> getSubscribeConfig(@RequestParam Integer page, @RequestParam Integer limit) {
        DataResponse<List<SubscribeConfigDto>> dataResponse = new DataResponse<>();
        RobotPage<SubscribeConfigDto> robotPage = subscribeConfigInfoService.getSubscribeConfig(page, limit);
        dataResponse.setCount(robotPage.getTotal());
        dataResponse.setData(robotPage.getList());
        return dataResponse;

    }

    @PostMapping("/updateSubscribeConfig")
    public DataResponse<?> updateSubscribeConfig(@RequestBody SubscribeConfigDto subscribeConfigDto) {
        subscribeConfigInfoService.updateSubscribeConfig(subscribeConfigDto);
        return new DataResponse<>();
    }

    @PostMapping("/addSubscribeConfig")
    public DataResponse<?> addSubscribeConfig(@RequestBody SubscribeConfigDto subscribeConfigDto) {
        subscribeConfigInfoService.addSubscribeConfig(subscribeConfigDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteSubscribeConfig")
    public DataResponse<?> deleteSubscribeConfig(@RequestParam String id) {
        subscribeConfigInfoService.deleteSubscribeConfig(id);
        return new DataResponse<>();
    }

    @GetMapping("/deleteBatchSubscribeConfig")
    public DataResponse<?> deleteBatchSubscribeConfig(@RequestParam List<String> ids) {
        subscribeConfigInfoService.deleteBatchSubscribeConfig(ids);
        return new DataResponse<>();
    }
}
