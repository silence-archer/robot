package com.silence.robot.mapper;

import java.util.List;

import com.silence.robot.model.TAutoTest;
import com.silence.robot.model.TAutoTestLogDetail;

public interface TAutoTestLogDetailMapper {

    List<TAutoTestLogDetail> selectAll();
    TAutoTestLogDetail selectByPrimaryKey(String id);
    void deleteByPrimaryKey(String id);
    void insert(TAutoTestLogDetail record);
    void updateByPrimaryKey(TAutoTestLogDetail record);

    List<TAutoTestLogDetail> selectByBatchNo(String batchNo);
}
