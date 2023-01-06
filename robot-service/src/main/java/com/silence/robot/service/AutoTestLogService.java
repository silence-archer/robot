package com.silence.robot.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.AutoTestDetailDto;
import com.silence.robot.domain.AutoTestLogDetailDto;
import com.silence.robot.domain.AutoTestLogDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.mapper.TAutoTestLogMapper;
import com.silence.robot.model.TAutoTestDetail;
import com.silence.robot.model.TAutoTestLog;
import com.silence.robot.utils.BeanUtils;

@Service
public class AutoTestLogService {

    @Resource
    private TAutoTestLogMapper autoTestLogMapper;

    public RobotPage<AutoTestLogDto> selectAllByPage(Integer page, Integer limit, String testCaseName) {
        PageHelper.startPage(page, limit);
        PageInfo<TAutoTestLog> pageInfo = new PageInfo<>(autoTestLogMapper.selectByTestCaseName(testCaseName));
        return new RobotPage<>(pageInfo.getTotal(), BeanUtils.copyList(AutoTestLogDto.class, pageInfo.getList()));
    }
}
