package com.silence.robot.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.constants.RobotConstants;
import com.silence.robot.context.GlobalContext;
import com.silence.robot.domain.AutoTestDetailDto;
import com.silence.robot.domain.AutoTestDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.enumeration.ProcessStatusEnum;
import com.silence.robot.enumeration.SceneTypeEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.mapper.TAutoTestDetailMapper;
import com.silence.robot.mapper.TAutoTestLogDetailMapper;
import com.silence.robot.mapper.TAutoTestMapper;
import com.silence.robot.mapper.TDataDictMapper;
import com.silence.robot.mapper.TFileConfigMapper;
import com.silence.robot.mapper.TInterfaceSceneMapper;
import com.silence.robot.mapper.TSubscribeConfigInfoMapper;
import com.silence.robot.model.TAutoTest;
import com.silence.robot.model.TAutoTestDetail;
import com.silence.robot.model.TAutoTestLogDetail;
import com.silence.robot.model.TFileConfig;
import com.silence.robot.model.TInterfaceScene;
import com.silence.robot.model.TSubscribeConfigInfo;
import com.silence.robot.service.autoTest.AutoTestExecuteFactory;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.HttpUtils;
import com.silence.robot.utils.TraceUtils;

@Service
public class AutoTestDetailService {

    private final Logger logger = LoggerFactory.getLogger(AutoTestDetailService.class);

    @Resource
    private TAutoTestDetailMapper autoTestDetailMapper;
    @Resource
    private TAutoTestMapper autoTestMapper;
    @Resource
    private TInterfaceSceneMapper interfaceSceneMapper;
    @Resource
    private TSubscribeConfigInfoMapper subscribeConfigInfoMapper;

    @Resource
    private TAutoTestLogDetailMapper autoTestLogDetailMapper;
    @Resource
    private TDataDictMapper dataDictMapper;

    public void add(AutoTestDetailDto autoTestDetail) {
        autoTestDetailMapper.insert(BeanUtils.copy(TAutoTestDetail.class, autoTestDetail));
    }

    public void update(AutoTestDetailDto autoTestDetail) {
        autoTestDetailMapper.updateByPrimaryKey(BeanUtils.copy(TAutoTestDetail.class, autoTestDetail));
    }

    public void delete(String id) {
        autoTestDetailMapper.deleteByPrimaryKey(id);
    }

    public AutoTestDetailDto selectById(String id) {
        return BeanUtils.copy(AutoTestDetailDto.class, autoTestDetailMapper.selectByPrimaryKey(id));
    }

    public RobotPage<AutoTestDetailDto> selectAllByPage(Integer page, Integer limit, String testCaseName) {
        PageHelper.startPage(page, limit);
        List<TAutoTestDetail> autoTestDetails = autoTestDetailMapper.selectByTestCaseName(testCaseName);
        PageInfo<TAutoTestDetail> pageInfo = new PageInfo<>(autoTestDetails);
        TAutoTest autoTest = autoTestMapper.selectByTestCaseName(testCaseName);
        List<AutoTestDetailDto> autoTestDetailDtos = BeanUtils.copyList(AutoTestDetailDto.class, pageInfo.getList());
        autoTestDetailDtos.forEach(autoTestDetailDto -> {
            autoTestDetailDto.setTestCaseDesc(autoTest.getTestCaseDesc());
            autoTestDetailDto.setBusinessTypeDesc(dataDictMapper.selectByNameAndEnumName("businessType",
                autoTestDetailDto.getBusinessType()).getEnumDesc());
            TInterfaceScene interfaceScene = interfaceSceneMapper.selectBySceneId(autoTestDetailDto.getSceneId());
            autoTestDetailDto.setSceneDesc(interfaceScene.getSceneDesc());
            autoTestDetailDto.setTranCode(interfaceScene.getTranCode());
            autoTestDetailDto.setTranDesc(interfaceScene.getTranDesc());
        });
        return new RobotPage<>(pageInfo.getTotal(), autoTestDetailDtos);
    }

    public List<AutoTestDetailDto> selectByTestCaseName(String testCaseName) {
        return BeanUtils.copyList(AutoTestDetailDto.class, autoTestDetailMapper.selectByTestCaseName(testCaseName));
    }

    public void execute(String testCaseName, String batchNo) {
        autoTestDetailMapper.selectByTestCaseName(testCaseName).forEach(autoTestDetail -> {
            TAutoTestLogDetail autoTestLogDetail = new TAutoTestLogDetail();
            autoTestLogDetail.setBatchNo(batchNo);
            autoTestLogDetail.setBusinessType(autoTestDetail.getBusinessType());
            autoTestLogDetail.setSceneId(autoTestDetail.getSceneId());
            try {
                AutoTestExecuteFactory.getByCode(SceneTypeEnum.valueOf(autoTestDetail.getSceneType())).execute(autoTestDetail.getSceneId(), autoTestDetail.getBusinessType());
                autoTestLogDetail.setResponseCode("000000");
                autoTestLogDetail.setResponseMessage("执行成功");
                autoTestLogDetailMapper.insert(autoTestLogDetail);
            } catch (BusinessException e) {
                autoTestLogDetail.setResponseCode(e.getCode()+"");
                autoTestLogDetail.setResponseMessage(e.getMessage());
                autoTestLogDetailMapper.insert(autoTestLogDetail);
                throw e;
            }
            try {
                Thread.sleep(autoTestDetail.getDelayTime());
            } catch (InterruptedException e) {
                logger.error("线程被打断", e);
            }
        });
    }

    public void deleteBatch(List<String> ids) {
        ids.forEach(id -> autoTestDetailMapper.deleteByPrimaryKey(id));
    }
}
