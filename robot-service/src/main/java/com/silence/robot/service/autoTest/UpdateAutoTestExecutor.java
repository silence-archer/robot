package com.silence.robot.service.autoTest;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;

import com.silence.robot.enumeration.SceneTypeEnum;
import com.silence.robot.mapper.TDatabaseInfoMapper;
import com.silence.robot.mapper.TInterfaceSceneMapper;
import com.silence.robot.model.TDatabaseInfo;
import com.silence.robot.model.TInterfaceScene;
import com.silence.robot.service.InterfaceSceneService;
import com.silence.robot.utils.CommonUtils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

@Service
public class UpdateAutoTestExecutor implements IAutoTestExecutor {

    @Resource
    private TDatabaseInfoMapper databaseInfoMapper;
    @Resource
    private InterfaceSceneService interfaceSceneService;
    @Override
    public void execute(String sceneId, String businessType) {
        TDatabaseInfo databaseInfo = databaseInfoMapper.selectByBusinessType(businessType);
        if (databaseInfo.getType().equals("redis")) {
            RedisClient redisClient = RedisClient.create(databaseInfo.getUrl());
            StatefulRedisConnection<String, String> connect = redisClient.connect();
            List<String> keys = connect.sync()
                .keys("*");
            keys.forEach(key -> connect.sync().del(key));
            connect.close();
            redisClient.shutdown();
            return;
        }
        String sceneValue = interfaceSceneService.getSceneBySceneIdVersion217(sceneId).replaceAll(";", "");
        CommonUtils.executeBatchSql(databaseInfo, Arrays.asList(sceneValue.split("\r\n")));
    }

    @Override
    public SceneTypeEnum getCode() {
        return SceneTypeEnum.UPDATE_TYPE;
    }
}
