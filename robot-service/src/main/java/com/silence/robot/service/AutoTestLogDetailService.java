package com.silence.robot.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.AutoTestLogDetailDto;
import com.silence.robot.domain.AutoTestLogDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.mapper.TAutoTestLogDetailMapper;
import com.silence.robot.mapper.TAutoTestLogMapper;
import com.silence.robot.model.TAutoTestLog;
import com.silence.robot.model.TAutoTestLogDetail;
import com.silence.robot.utils.BeanUtils;

@Service
public class AutoTestLogDetailService {

    @Resource
    private TAutoTestLogDetailMapper autoTestLogDetailMapper;

    public List<AutoTestLogDetailDto> selectByBatchNo(String batchNo) {
        return BeanUtils.copyList(AutoTestLogDetailDto.class, autoTestLogDetailMapper.selectByBatchNo(batchNo));
    }

    public RobotPage<AutoTestLogDetailDto> selectAllByPage(Integer page, Integer limit, String batchNo) {
        PageHelper.startPage(page, limit);
        PageInfo<TAutoTestLogDetail> pageInfo = new PageInfo<>(autoTestLogDetailMapper.selectByBatchNo(batchNo));
        return new RobotPage<>(pageInfo.getTotal(), BeanUtils.copyList(AutoTestLogDetailDto.class, pageInfo.getList()));
    }
}
