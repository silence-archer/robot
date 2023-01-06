package com.silence.robot.mapper;

import java.util.List;

import com.silence.robot.model.TAutoTest;
import com.silence.robot.model.TAutoTestDetail;

public interface TAutoTestDetailMapper {

    List<TAutoTestDetail> selectAll();
    TAutoTestDetail selectByPrimaryKey(String id);
    void deleteByPrimaryKey(String id);
    void insert(TAutoTestDetail record);
    void updateByPrimaryKey(TAutoTestDetail record);

    List<TAutoTestDetail> selectByTestCaseName(String testCaseName);
}
