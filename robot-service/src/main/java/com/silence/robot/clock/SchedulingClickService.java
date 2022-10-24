package com.silence.robot.clock;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.clock.DingClockDto;
import com.silence.robot.domain.clock.SaveSignRuleDate;
import com.silence.robot.domain.clock.SignRuleDateResult;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.service.SubscribeConfigInfoService;
import com.silence.robot.utils.HttpUtils;

@Service
public class SchedulingClickService {

    private final static Logger log = LoggerFactory.getLogger(SchedulingClickService.class);

    @Resource
    private DingClockService dingClockService;
    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;
    @Resource
    private MailSendService mailSendService;

    public void firstClick() {

        for(DingClockDto bootStrap : dingClockService.getEffectiveList()){

            try {
                onceRun(bootStrap);
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                log.error("打卡失败 发送短信", e);
                if(null!=bootStrap.getMailAddress()){
                    mailSendService.send(bootStrap.getUsername()+"打卡程序执行异常", Arrays.toString(e.getStackTrace()), bootStrap.getMailAddress());
                }
            }

        }

    }

    public void onceRun(DingClockDto bootStrap) {
        SaveSignRuleDate saveSignRuleDate = new SaveSignRuleDate();
        BeanUtils.copyProperties(bootStrap,saveSignRuleDate);
        String addressUrl = subscribeConfigInfoService.getConfigValue(ConfigEnum.DING_CLOCK_ADDRESS);
        String getCardUrl = subscribeConfigInfoService.getConfigValue(ConfigEnum.DING_CLOCK_GET_CARD);
        String getOpenIdUrl = subscribeConfigInfoService.getConfigValue(ConfigEnum.DING_CLOCK_GET_OPEN_ID);
        String getWaitDetailUrl = subscribeConfigInfoService.getConfigValue(ConfigEnum.DING_CLOCK_GET_WAIT_DEAL);
        String getSignRuleDateUrl = subscribeConfigInfoService.getConfigValue(ConfigEnum.DING_CLOCK_GET_SIGN_RULE_DATE);
        String saveSignRuleDateUrl = subscribeConfigInfoService.getConfigValue(ConfigEnum.DING_CLOCK_SAVE_SIGN_RULE_DATE);
        if(null != bootStrap.getOpenId() && !"".equals(bootStrap.getOpenId()
            .trim())){
            HttpUtils.doGet(addressUrl+getCardUrl+bootStrap.getUserId().toString());
            HttpUtils.doGet(addressUrl+getWaitDetailUrl+bootStrap.getUserId().toString());
            JSONObject result1 = HttpUtils.doGet(addressUrl+getSignRuleDateUrl+bootStrap.getOpenId());
            SignRuleDateResult signRuleDateResult = result1.toJavaObject(SignRuleDateResult.class);
            if (null != signRuleDateResult){
                saveSignRuleDate(signRuleDateResult, saveSignRuleDate);
            }
        }
        checkDto(saveSignRuleDate);
        log.info(saveSignRuleDate.toString());
        JSONObject result = HttpUtils.doPost(saveSignRuleDateUrl, JSONObject.toJSONString(saveSignRuleDate));
        if(!"true".equals(result.get("success")
            .toString())){
            log.error("打卡失败");
            throw new BusinessException("打卡失败");
        }
        log.info("打卡成功 : {}", bootStrap);
    }


    private void saveSignRuleDate(SignRuleDateResult signRuleDateResult , SaveSignRuleDate saveSignRuleDate ){

        saveSignRuleDate.setUserId(signRuleDateResult.getData().getEmployeeId());
        saveSignRuleDate.setProjectId(signRuleDateResult.getData().getProjectId());
        saveSignRuleDate.setRuleId(signRuleDateResult.getData().getID());
        saveSignRuleDate.setAddrId(signRuleDateResult.getData().getAddRessList().get(0).getId());
        saveSignRuleDate.setApprUserId(signRuleDateResult.getData().getApprUserId());
        saveSignRuleDate.setDeptId(signRuleDateResult.getData().getDeptId());
        saveSignRuleDate.setWorkReportType("1");
        saveSignRuleDate.setPbflag("0");
        saveSignRuleDate.setImagePath("");
        saveSignRuleDate.setBeforeup(signRuleDateResult.getData().getBEFOREUP());
        saveSignRuleDate.setItcode(signRuleDateResult.getData().getLoginname());
        saveSignRuleDate.setSbuId(signRuleDateResult.getData().getSbuid());
    }

    private void checkDto(Object object) {
        if (null == object) {
            log.error("*******************对象为空");
            log.error("*****************请检查配置配置信息************************");
            throw new NullPointerException();
        }
        //循环遍历对象属性
        for(Field f : object.getClass().getDeclaredFields()){
            f.setAccessible(true);
            //判断对象属性是否为空
            try {
                if(f.get(object) == null){
                    log.error("*******************"+f.getName() + "为空");
                    log.error("*****************请检查配置配置信息************************");
                    throw new BusinessException(f.getName() + "为空");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
