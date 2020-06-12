/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SubscribeConfigInfoService
 * Author:   silence
 * Date:     2020/2/16 21:49
 * Description: 杂类参数service
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TSubscribeConfigInfoMapper;
import com.silence.robot.model.TSubscribeConfigInfo;
import com.silence.robot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈杂类参数service〉
 *
 * @author silence
 * @create 2020/2/16
 * @since 1.0.0
 */
@Service
public class SubscribeConfigInfoService {

    private final Logger logger = LoggerFactory.getLogger(SubscribeConfigInfoService.class);

    @Resource
    private TSubscribeConfigInfoMapper subscribeConfigInfoMapper;

    public String getConfigValue(ConfigEnum configEnum){
        TSubscribeConfigInfo subscribeConfigInfo = subscribeConfigInfoMapper.selectByConfigName(configEnum.getName());
        if(subscribeConfigInfo == null){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        String configValue = subscribeConfigInfo.getConfigValue();
        logger.info("当前配置名称{}对应配置值为{}",configEnum.getName(),configValue);
        return configValue;
    }

    public void setConfigValue(ConfigEnum configEnum, String configValue, Integer expireTime){
        TSubscribeConfigInfo subscribeConfigInfo = subscribeConfigInfoMapper.selectByConfigName(configEnum.getName());
        if(subscribeConfigInfo == null){
            subscribeConfigInfo = new TSubscribeConfigInfo();
            subscribeConfigInfo.setConfigName(configEnum.getName());
            subscribeConfigInfo.setConfigDesc(configEnum.getDesc());
            subscribeConfigInfo.setConfigValue(configValue);
            subscribeConfigInfo.setExpireTime(expireTime);
            subscribeConfigInfoMapper.insert(subscribeConfigInfo);
        }else{
            subscribeConfigInfo.setConfigValue(configValue);
            subscribeConfigInfo.setExpireTime(expireTime);
            subscribeConfigInfoMapper.updateByPrimaryKey(subscribeConfigInfo);
        }

    }
}