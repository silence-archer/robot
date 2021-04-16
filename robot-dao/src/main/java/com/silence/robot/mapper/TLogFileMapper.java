package com.silence.robot.mapper;

import com.silence.robot.model.TLogFile;

import java.util.List;

public interface TLogFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(TLogFile record);
    int insertAndBatch(List<TLogFile> records);

    int insertSelective(TLogFile record);

    TLogFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TLogFile record);

    int updateByPrimaryKey(TLogFile record);
}