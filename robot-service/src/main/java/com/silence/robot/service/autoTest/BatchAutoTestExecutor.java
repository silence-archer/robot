package com.silence.robot.service.autoTest;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.context.GlobalContext;
import com.silence.robot.enumeration.SceneTypeEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TDatabaseInfoMapper;
import com.silence.robot.mapper.TFileConfigMapper;
import com.silence.robot.mapper.TInterfaceSceneMapper;
import com.silence.robot.model.TDatabaseInfo;
import com.silence.robot.model.TFileConfig;
import com.silence.robot.model.TInterfaceScene;
import com.silence.robot.service.InterfaceSceneService;
import com.silence.robot.service.LoanService;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;

@Service
public class BatchAutoTestExecutor implements IAutoTestExecutor {

    private Logger logger = LoggerFactory.getLogger(BatchAutoTestExecutor.class);

    @Resource
    private TInterfaceSceneMapper interfaceSceneMapper;
    @Resource
    private TFileConfigMapper fileConfigMapper;
    @Resource
    private TDatabaseInfoMapper databaseInfoMapper;
    @Resource
    private InterfaceSceneService interfaceSceneService;
    @Override
    public void execute(String sceneId, String businessType) {
        TInterfaceScene interfaceScene = interfaceSceneMapper.selectBySceneId(sceneId);
        TFileConfig fileConfig = fileConfigMapper.selectByBusinessType(businessType);
        HashMap<String, String> cookies = new HashMap<>(1);
        cookies.put("XXL_JOB_LOGIN_IDENTITY", GlobalContext.getHttpClientContext("XXL_JOB_LOGIN_IDENTITY").toString());
        JSONObject param = JSONObject.parseObject(interfaceScene.getSceneValue());
        String tranCode = interfaceScene.getTranCode()+"?id="+ param.getString("id");
        JSONObject jsonObject = HttpUtils.doPost(
            "http://" + fileConfig.getRemoteIp() + ":" + fileConfig.getRemotePort() + tranCode,
            cookies);
        if (jsonObject.getInteger("code") != 200) {
            throw new BusinessException(jsonObject.getString("msg"));
        }
        GlobalContext.setHttpClientContext("jobId", param.getString("id"));
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                logger.error("线程被打断", e);
            }
            TInterfaceScene scene = interfaceSceneMapper.selectByTranCode("day_end_batch_check")
                .get(0);
            TDatabaseInfo databaseInfo = databaseInfoMapper.selectByBusinessType(businessType);
            String sceneValue = interfaceSceneService.getSceneBySceneIdVersion217(scene.getSceneId());
            List<JSONObject> jsonObjects = CommonUtils.getResultSetByDataBase(databaseInfo, sceneValue);
            if (CommonUtils.isEmpty(jsonObjects)) {
                throw new BusinessException(ExceptionCode.DAY_END_BATCH_PROCESS_ERROR);
            }
            if (jsonObjects.get(0).getInteger("trigger_code") == 200
                && CommonUtils.isNotEmpty(jsonObjects.get(0).getString("trigger_msg"))
                && jsonObjects.get(0).getInteger("handle_code") == 200
                && CommonUtils.isEmpty(jsonObjects.get(0).getString("handle_msg"))) {
                return;
            }
            if (jsonObjects.get(0).getInteger("trigger_code") != 200
                || (jsonObjects.get(0).getInteger("handle_code") != 200 && jsonObjects.get(0).getInteger("handle_code") != 0)) {
                throw new BusinessException(ExceptionCode.DAY_END_BATCH_PROCESS_ERROR);
            }

        }


    }

    @Override
    public SceneTypeEnum getCode() {
        return SceneTypeEnum.BATCH_TYPE;
    }
}
