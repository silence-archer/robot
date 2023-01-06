package com.silence.robot.service.autoTest;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.enumeration.SceneTypeEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TDatabaseInfoMapper;
import com.silence.robot.mapper.TFileConfigMapper;
import com.silence.robot.mapper.TInterfaceSceneMapper;
import com.silence.robot.model.TDatabaseInfo;
import com.silence.robot.model.TInterfaceScene;
import com.silence.robot.service.InterfaceSceneService;
import com.silence.robot.utils.CommonUtils;

@Service
public class CheckAutoTestExecutor implements IAutoTestExecutor {

    @Resource
    private TDatabaseInfoMapper databaseInfoMapper;
    @Resource
    private InterfaceSceneService interfaceSceneService;

    @Override
    public void execute(String sceneId, String businessType) {
        TDatabaseInfo databaseInfo = databaseInfoMapper.selectByBusinessType(businessType);
        String sceneValue = interfaceSceneService.getSceneBySceneIdVersion217(sceneId).replaceAll(";", "");
        Arrays.asList(sceneValue.split("\n")).forEach(sql -> {
            List<JSONObject> list = CommonUtils.getResultSetByDataBase(databaseInfo, sql);
            if (CommonUtils.isEmpty(list)) {
                throw new BusinessException(ExceptionCode.EMPTY_ERROR);
            }
        });

    }

    @Override
    public SceneTypeEnum getCode() {
        return SceneTypeEnum.CHECK_TYPE;
    }
}
