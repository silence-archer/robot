/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SubscribeAccessTokenJob
 * Author:   silence
 * Date:     2019/12/30 15:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.job.task;

import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.job.RobotQuartzJob;
import com.silence.robot.job.RobotQuartzTask;
import com.silence.robot.mapper.TSubscribeConfigInfoMapper;
import com.silence.robot.model.TSubscribeConfigInfo;
import com.silence.robot.utils.CommonUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @create 2019/12/30
 * @since 1.0.0
 */
@Component
public class SubscribeAccessTokenJob implements RobotQuartzTask {

    private final Logger logger = LoggerFactory.getLogger(SubscribeAccessTokenJob.class);

    @Value("${silence.wechat.subscribe.api}")
    private String api;

    @Value("${silence.wechat.subscribe.grant_type}")
    private String grantType;

    @Value("${silence.wechat.subscribe.appid}")
    private String appId;

    @Value("${silence.wechat.subscribe.secret}")
    private String secret;

    @Resource
    private TSubscribeConfigInfoMapper subscribeConfigInfoMapper;

    @Override
    public void execute() {
        StringBuilder uri = new StringBuilder();
        uri.append(api).append("?").append("grant_type=").append(grantType).append("&appid=").append(appId).append("&secret=").append(secret);
        HttpGet request = new HttpGet(uri.toString());
        Map map = CommonUtils.httpClientExecute(request);
        TSubscribeConfigInfo accessToken = subscribeConfigInfoMapper.selectByConfigName("access_token");
        if(accessToken == null){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        if(CommonUtils.isEmpty(map.get("access_token"))){
            logger.error("返回的错误码为{}，错误信息为{}", map.get("errcode"), map.get("errmsg"));
            throw new BusinessException(ExceptionCode.SUBSCRIBE_TOKEN_ERROR);
        }
        accessToken.setConfigValue((String) map.get("access_token"));
        accessToken.setExpireTime(Integer.valueOf(map.get("expires_in").toString()));
        accessToken.setUpdateTime(new Date());
        subscribeConfigInfoMapper.updateByPrimaryKey(accessToken);



    }
}