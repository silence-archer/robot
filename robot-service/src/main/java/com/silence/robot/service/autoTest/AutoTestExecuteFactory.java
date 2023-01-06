package com.silence.robot.service.autoTest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silence.robot.enumeration.SceneTypeEnum;

@Service
public class AutoTestExecuteFactory {

    private static List<IAutoTestExecutor> autoTestExecutors;

    public static IAutoTestExecutor getByCode(SceneTypeEnum sceneTypeEnum) {
        return autoTestExecutors.stream().filter(iAutoTestExecutor -> iAutoTestExecutor.getCode() == sceneTypeEnum).findAny().orElse(null);
    }

    @Autowired
    public void setAutoTestExecutors(List<IAutoTestExecutor> autoTestExecutors) {
        AutoTestExecuteFactory.autoTestExecutors = autoTestExecutors;
    }
}
