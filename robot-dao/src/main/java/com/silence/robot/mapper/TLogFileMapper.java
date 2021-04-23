package com.silence.robot.mapper;

import com.silence.robot.model.TLogFile;

import java.util.List;
import java.util.Map;

public interface TLogFileMapper {
    int deleteByPrimaryKey(String id);
    int deleteByCondition(Map<String, Object> param);

    int insert(TLogFile record);

    int insertSelective(TLogFile record);

    TLogFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TLogFile record);

    int updateByPrimaryKey(TLogFile record);

    void insertAndBatch(List<TLogFile> records);

    List<TLogFile> selectByCondition(Map<String, Object> param);
}