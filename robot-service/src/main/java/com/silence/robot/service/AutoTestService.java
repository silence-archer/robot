package com.silence.robot.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.context.GlobalContext;
import com.silence.robot.domain.AutoTestDto;
import com.silence.robot.domain.AutoTestLogDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.enumeration.ProcessStatusEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.mapper.TAutoTestLogMapper;
import com.silence.robot.mapper.TAutoTestMapper;
import com.silence.robot.mapper.TSubscribeConfigInfoMapper;
import com.silence.robot.model.TAutoTest;
import com.silence.robot.model.TAutoTestDetail;
import com.silence.robot.model.TAutoTestLog;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.TraceUtils;

@Service
public class AutoTestService {


    private final Logger logger = LoggerFactory.getLogger(AutoTestService.class);
    @Resource
    private TAutoTestMapper autoTestMapper;
    @Resource
    private TAutoTestLogMapper autoTestLogMapper;
    @Resource
    private TSubscribeConfigInfoMapper subscribeConfigInfoMapper;
    @Resource
    private SequenceService sequenceService;
    @Resource
    private AutoTestDetailService autoTestDetailService;

    public void add(AutoTestDto autoTestDto) {
        autoTestDto.setTestCaseName("TestCase"+sequenceService.getSequence("TestCase"));
        autoTestMapper.insert(BeanUtils.copy(TAutoTest.class, autoTestDto));
    }

    public void update(AutoTestDto autoTestDto) {
        autoTestMapper.updateByPrimaryKey(BeanUtils.copy(TAutoTest.class, autoTestDto));
    }

    public void delete(String id) {
        autoTestMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<String> ids) {
        ids.forEach(id -> {
            autoTestMapper.deleteByPrimaryKey(id);
        });
    }

    public List<AutoTestDto> selectAll() {
        return BeanUtils.copyList(AutoTestDto.class, autoTestMapper.selectAll());
    }

    public RobotPage<AutoTestDto> selectAllByPage(Integer page, Integer limit, String testCaseDesc) {
        PageHelper.startPage(page, limit);
        PageInfo<TAutoTest> pageInfo = new PageInfo<>(autoTestMapper.selectByTestCaseDesc(testCaseDesc));
        return new RobotPage<>(pageInfo.getTotal(), BeanUtils.copyList(AutoTestDto.class, pageInfo.getList()));

    }

    public AutoTestDto selectById(String id) {
        return BeanUtils.copy(AutoTestDto.class, autoTestMapper.selectByPrimaryKey(id));
    }

    public AutoTestDto selectByTestCaseName(String testCaseName) {
        return BeanUtils.copy(AutoTestDto.class, autoTestMapper.selectByTestCaseName(testCaseName));
    }

    @Async
    public void run(String testCaseName) {
        String batchNo = "BatchNo" + sequenceService.getSequence("batchNo");
        onceRun(testCaseName, batchNo);
    }

    public void onceRun(String testCaseName, String batchNo) {
        onceRun("default", testCaseName, batchNo);
    }

    public void onceRun(String jobName, String testCaseName, String batchNo) {
        logger.info("{}-{}-{}-开始执行>>>>>>>", batchNo, jobName, testCaseName);
        TAutoTestLog autoTestLog = new TAutoTestLog();
        autoTestLog.setBatchNo(batchNo);
        autoTestLog.setTestCaseName(testCaseName);
        autoTestLog.setJobName(jobName);
        autoTestLog.setStatus(ProcessStatusEnum.PROCESSING.getCode());
        autoTestLogMapper.insert(autoTestLog);
        try {
            GlobalContext.initHttpClientContext();
            subscribeConfigInfoMapper.selectByCreateUser(TraceUtils.getParentLoginUsername()).forEach(tSubscribeConfigInfo -> GlobalContext.setHttpClientContext(tSubscribeConfigInfo.getConfigName(), tSubscribeConfigInfo.getConfigValue()));
            autoTestDetailService.execute(testCaseName, batchNo);
            autoTestLog.setStatus(ProcessStatusEnum.SUCCESS.getCode());
            autoTestLogMapper.updateByPrimaryKey(autoTestLog);
        } catch (BusinessException e) {
            logger.error("{}-{}-自动化测试案例执行失败", testCaseName, batchNo, e);
            autoTestLog.setStatus(ProcessStatusEnum.FAIL.getCode());
            autoTestLogMapper.updateByPrimaryKey(autoTestLog);
            throw e;
        }
    }
}
