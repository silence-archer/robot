package com.silence.robot.controller;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.InterfaceSceneDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.InterfaceSceneService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/8
 */
@RestController
public class InterfaceSceneController {

    @Resource
    private InterfaceSceneService interfaceSceneService;
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

    @GetMapping("/getSceneBySceneId")
    public DataResponse<JSONObject> getSceneBySceneId(@RequestParam String sceneId) {
        return new DataResponse<>(interfaceSceneService.getSceneBySceneId(sceneId));
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
    @RequiresRoles("roleNo0001")
    public DataResponse<?> deleteInterfaceScene(@RequestParam String id){
        interfaceSceneService.deleteInterfaceScene(id);
        return new DataResponse<>();
    }

    @GetMapping("/deleteBatchInterfaceScene")
    @RequiresRoles("roleNo0001")
    public DataResponse<?> deleteBatchInterfaceScene(@RequestParam List<String> ids){
        interfaceSceneService.deleteBatchInterfaceScene(ids);
        return new DataResponse<>();
    }
}
