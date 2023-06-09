package com.silence.robot.controller;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.InterfaceSceneDropdownDto;
import com.silence.robot.domain.InterfaceSceneDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.service.InterfaceSceneService;
import com.silence.robot.service.SubscribeConfigInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author silence
 * @since 2021/9/8
 */
@RestController
public class InterfaceSceneController {

    @Resource
    private InterfaceSceneService interfaceSceneService;
    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;
    @GetMapping("/getInterfaceScene")
    public DataResponse<List<InterfaceSceneDto>> getInterfaceScene(@RequestParam Integer page, @RequestParam Integer limit) {

        RobotPage<InterfaceSceneDto> robotPage = interfaceSceneService.getScene(page, limit);

        DataResponse<List<InterfaceSceneDto>> dataResponse = new DataResponse<>(robotPage.getList());
        dataResponse.setCount(robotPage.getTotal());
        return dataResponse;
    }

    @GetMapping("/getSceneByTranCode")
    public DataResponse<List<InterfaceSceneDto>> getSceneByTranCode(@RequestParam String tranCode, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {

        RobotPage<InterfaceSceneDto> robotPage = interfaceSceneService.getSceneByTranCode(tranCode, page, limit);

        DataResponse<List<InterfaceSceneDto>> dataResponse = new DataResponse<>(robotPage.getList());
        dataResponse.setCount(robotPage.getTotal());
        return dataResponse;
    }

    @GetMapping("/getSceneTranCode")
    public DataResponse<List<InterfaceSceneDropdownDto>> getSceneTranCode() {

        return new DataResponse<>(interfaceSceneService.getSceneTranCode());
    }

    @GetMapping("/getSceneBySceneId")
    public DataResponse<JSONObject> getSceneBySceneId(@RequestParam String sceneId) {
        String configValue = subscribeConfigInfoService.getConfigValue(ConfigEnum.FREE_MARKER_VERSION_ENUM);
        JSONObject scene;
        if ("2.0".equals(configValue)) {
            scene = interfaceSceneService.getSceneBySceneIdVersion2(sceneId);
        }else if ("3.0".equals(configValue)){
            scene = interfaceSceneService.getSceneBySceneIdVersion3(sceneId);
        } else {
            scene = interfaceSceneService.getSceneBySceneId(sceneId);
        }

        return new DataResponse<>(scene);
    }

    @PostMapping("/addInterfaceScene")
    public DataResponse<?> addInterfaceScene(@RequestBody InterfaceSceneDto interfaceSceneDto){
        interfaceSceneService.addInterfaceScene(interfaceSceneDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateInterfaceScene")
    public DataResponse<?> updateInterfaceScene(@RequestBody InterfaceSceneDto interfaceSceneDto){
        interfaceSceneService.updateInterfaceScene(interfaceSceneDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteInterfaceScene")
    @RequiresPermissions("SCENE:DELETE")
    public DataResponse<?> deleteInterfaceScene(@RequestParam String id){
        interfaceSceneService.deleteInterfaceScene(id);
        return new DataResponse<>();
    }

    @GetMapping("/deleteBatchInterfaceScene")
    @RequiresPermissions("SCENE:DELETE")
    public DataResponse<?> deleteBatchInterfaceScene(@RequestParam List<String> ids){
        interfaceSceneService.deleteBatchInterfaceScene(ids);
        return new DataResponse<>();
    }
}
