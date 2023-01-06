package com.silence.robot.mapper;

import java.util.List;

import com.silence.robot.model.TAutoTest;

public interface TAutoTestMapper {

    List<TAutoTest> selectAll();
    List<TAutoTest> selectByTestCaseDesc(String testCaseDesc);
    TAutoTest selectByPrimaryKey(String id);
    void deleteByPrimaryKey(String id);
    void insert(TAutoTest record);
    void updateByPrimaryKey(TAutoTest record);

    TAutoTest selectByTestCaseName(String testCaseName);
    List<TAutoTest> selectByJobName(String jobName);
}
