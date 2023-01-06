package com.silence.robot.service.autoTest;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.context.GlobalContext;
import com.silence.robot.enumeration.SceneTypeEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.mapper.TAutoTestDetailMapper;
import com.silence.robot.mapper.TFileConfigMapper;
import com.silence.robot.mapper.TInterfaceSceneMapper;
import com.silence.robot.mapper.TSubscribeConfigInfoMapper;
import com.silence.robot.model.TFileConfig;
import com.silence.robot.model.TInterfaceScene;
import com.silence.robot.model.TSubscribeConfigInfo;
import com.silence.robot.service.InterfaceSceneService;
import com.silence.robot.service.LoanService;

@Service
public class OltpAutoTestExecutor implements IAutoTestExecutor {

    @Resource
    private TInterfaceSceneMapper interfaceSceneMapper;
    @Resource
    private InterfaceSceneService interfaceSceneService;
    @Resource
    private TFileConfigMapper fileConfigMapper;
    @Resource
    private LoanService loanService;
    @Resource
    private TSubscribeConfigInfoMapper subscribeConfigInfoMapper;

    @Override
    public void execute(String sceneId, String businessType) {
        TInterfaceScene interfaceScene = interfaceSceneMapper.selectBySceneId(sceneId);
        TFileConfig fileConfig = fileConfigMapper.selectByBusinessType(businessType);
        String sceneValue = interfaceSceneService.getSceneBySceneIdVersion217(sceneId);
        GlobalContext.setHttpClientContext("serial_no", System.currentTimeMillis()+"");
        JSONObject response = loanService.executeLoan217(fileConfig.getRemoteIp(), fileConfig.getRemotePort(),
            interfaceScene.getTranCode(), sceneValue);
        TSubscribeConfigInfo loan306SuccessCode = subscribeConfigInfoMapper.selectByConfigName("loan306_success_code");
        if (response.getString("responseType")
            .equals(loan306SuccessCode.getConfigValue()) && response.getString("httpStatus")
            .equals("200") && response.getString("rpcResult")
            .equals("000000")) {
            return;
        }
        throw new BusinessException(response.getString("errorMessage"));
    }

    @Override
    public SceneTypeEnum getCode() {
        return SceneTypeEnum.OLTP_TYPE;
    }
}
