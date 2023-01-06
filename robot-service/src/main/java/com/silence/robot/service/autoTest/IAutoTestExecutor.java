package com.silence.robot.service.autoTest;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.enumeration.SceneTypeEnum;

public interface IAutoTestExecutor {

    void execute(String sceneId, String businessType);

    SceneTypeEnum getCode();
}
