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
import com.silence.robot.domain.AutoTestJobDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.enumeration.ProcessStatusEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.mapper.TAutoTestJobMapper;
import com.silence.robot.mapper.TAutoTestLogMapper;
import com.silence.robot.mapper.TAutoTestMapper;
import com.silence.robot.mapper.TSubscribeConfigInfoMapper;
import com.silence.robot.model.TAutoTestJob;
import com.silence.robot.model.TAutoTestJob;
import com.silence.robot.model.TAutoTestLog;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.TraceUtils;

@Service
public class AutoTestJobService {

    private final Logger logger = LoggerFactory.getLogger(AutoTestJobService.class);
    @Resource
    private TAutoTestJobMapper autoTestJobMapper;
    @Resource
    private TAutoTestMapper autoTestMapper;
    @Resource
    private TSubscribeConfigInfoMapper subscribeConfigInfoMapper;
    @Resource
    private SequenceService sequenceService;
    @Resource
    private AutoTestDetailService autoTestDetailService;
    @Resource
    private AutoTestService autoTestService;

    public void add(AutoTestJobDto autoTestJobDto) {
        autoTestJobDto.setJobName("JobTest"+sequenceService.getSequence("JobTest"));
        autoTestJobMapper.insert(BeanUtils.copy(TAutoTestJob.class, autoTestJobDto));
    }

    public void update(AutoTestJobDto autoTestJobDto) {
        autoTestJobMapper.updateByPrimaryKey(BeanUtils.copy(TAutoTestJob.class, autoTestJobDto));
    }

    public void delete(String id) {
        autoTestJobMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<String> ids) {
        ids.forEach(id -> {
            autoTestJobMapper.deleteByPrimaryKey(id);
        });
    }

    public List<AutoTestJobDto> selectAll() {
        return BeanUtils.copyList(AutoTestJobDto.class, autoTestJobMapper.selectAll());
    }

    public RobotPage<AutoTestJobDto> selectAllByPage(Integer page, Integer limit, String jobDesc) {
        PageHelper.startPage(page, limit);
        PageInfo<TAutoTestJob> pageInfo = new PageInfo<>(autoTestJobMapper.selectByJobDesc(jobDesc));
        return new RobotPage<>(pageInfo.getTotal(), BeanUtils.copyList(AutoTestJobDto.class, pageInfo.getList()));

    }

    public AutoTestJobDto selectById(String id) {
        return BeanUtils.copy(AutoTestJobDto.class, autoTestJobMapper.selectByPrimaryKey(id));
    }

    public AutoTestJobDto selectByJobName(String jobName) {
        return BeanUtils.copy(AutoTestJobDto.class, autoTestJobMapper.selectByJobName(jobName));
    }

    @Async
    public void run(String jobName) {
        String batchNo = "BatchNo" + sequenceService.getSequence("batchNo");
        autoTestMapper.selectByJobName(jobName).forEach(tAutoTest -> {
            for (int i = 0; i < tAutoTest.getLoopNum(); i++) {
                autoTestService.onceRun(jobName, tAutoTest.getTestCaseName(), batchNo);
            }
        });
    }
}
