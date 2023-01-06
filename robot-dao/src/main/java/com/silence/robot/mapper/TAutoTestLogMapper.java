package com.silence.robot.mapper;

import java.util.List;

import com.silence.robot.model.TAutoTest;
import com.silence.robot.model.TAutoTestLog;

public interface TAutoTestLogMapper {

    List<TAutoTestLog> selectAll();
    TAutoTestLog selectByPrimaryKey(String id);
    void deleteByPrimaryKey(String id);
    void insert(TAutoTestLog record);
    void updateByPrimaryKey(TAutoTestLog record);

    List<TAutoTestLog> selectByTestCaseName(String testCaseName);
}
