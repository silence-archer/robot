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

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.job.RobotQuartzTask;
import com.silence.robot.service.SubscribeConfigInfoService;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @since 2019/12/30
 * 
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
    private SubscribeConfigInfoService subscribeConfigInfoService;

    @Override
    public void execute() {
        HttpGet request = new HttpGet(api + "?" + "grant_type=" + grantType + "&appid=" + appId + "&secret=" + secret);
        JSONObject map = HttpUtils.httpClientExecute(request);
        if(CommonUtils.isEmpty(map.get("access_token"))){
            logger.error("返回的错误码为{}，错误信息为{}", map.get("errcode"), map.get("errmsg"));
            throw new BusinessException(ExceptionCode.SUBSCRIBE_TOKEN_ERROR);
        }
        subscribeConfigInfoService.setConfigValue(ConfigEnum.ACCESS_TOKEN_ENUM, CommonUtils.toString(map.get("access_token")), CommonUtils.toInteger(map.get("expires_in")));



    }
}