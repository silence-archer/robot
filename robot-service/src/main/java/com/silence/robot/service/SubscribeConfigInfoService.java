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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.SubscribeConfigDto;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TSubscribeConfigInfoMapper;
import com.silence.robot.model.TSubscribeConfigInfo;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        return getConfigValue(configEnum.getName(), null);
    }

    public String getConfigValue(String configName, String username){
        TSubscribeConfigInfo subscribeConfigInfo = subscribeConfigInfoMapper.selectByConfigName(configName);
        if(subscribeConfigInfo == null){
            throw new BusinessException(ExceptionCode.NO_EXIST_PARAM, username, configName);
        }
        if(CommonUtils.isNotEmpty(username) && CommonUtils.isNotEquals(username, subscribeConfigInfo.getCreateUser())) {
            throw new BusinessException(ExceptionCode.NO_EXIST_PARAM, username, configName);
        }
        String configValue = subscribeConfigInfo.getConfigValue();
        logger.info("当前配置名称{}对应配置值为{}",configName,configValue);
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

    public RobotPage<SubscribeConfigDto> getSubscribeConfig(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        String loginUserName = HttpUtils.getLoginUserName();
        List<TSubscribeConfigInfo> subscribeConfigInfos;
        if (CommonUtils.isEquals(loginUserName, "admin")) {
            subscribeConfigInfos = subscribeConfigInfoMapper.selectAll();
        }else {
            subscribeConfigInfos = subscribeConfigInfoMapper.selectByCreateUser(loginUserName);
        }

        PageInfo<TSubscribeConfigInfo> pageInfo = new PageInfo<>(subscribeConfigInfos);
        return new RobotPage<>(pageInfo.getTotal(), BeanUtils.copyList(SubscribeConfigDto.class, pageInfo.getList()));

    }

    public void updateSubscribeConfig(SubscribeConfigDto subscribeConfigDto) {
        subscribeConfigInfoMapper.updateByPrimaryKey(BeanUtils.copy(TSubscribeConfigInfo.class, subscribeConfigDto));
    }

    public void addSubscribeConfig(SubscribeConfigDto subscribeConfigDto) {
        subscribeConfigInfoMapper.insert(BeanUtils.copy(TSubscribeConfigInfo.class, subscribeConfigDto));
    }

    public void deleteSubscribeConfig(String id) {
        subscribeConfigInfoMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatchSubscribeConfig(List<String> ids) {
        subscribeConfigInfoMapper.deleteAndBatchById(ids);
    }
}