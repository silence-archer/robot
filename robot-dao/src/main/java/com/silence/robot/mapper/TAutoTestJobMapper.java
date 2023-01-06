package com.silence.robot.mapper;

import java.util.List;

import com.silence.robot.model.TAutoTestJob;

public interface TAutoTestJobMapper {

    List<TAutoTestJob> selectAll();
    List<TAutoTestJob> selectByJobDesc(String jobDesc);
    TAutoTestJob selectByPrimaryKey(String id);
    void deleteByPrimaryKey(String id);
    void insert(TAutoTestJob record);
    void updateByPrimaryKey(TAutoTestJob record);

    TAutoTestJob selectByJobName(String jobName);
}
