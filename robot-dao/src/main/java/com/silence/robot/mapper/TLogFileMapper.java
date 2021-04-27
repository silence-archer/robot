package com.silence.robot.mapper;

import com.silence.robot.model.TLogFile;

import java.util.List;
import java.util.Map;

public interface TLogFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(TLogFile record);

    int deleteByCondition(Map<String, Object> param);

    int insertAndBatch(List<TLogFile> records);

    int insertSelective(TLogFile record);

    TLogFile selectByPrimaryKey(String id);

    List<TLogFile> selectByCondition(Map<String, Object> param);

    int updateByPrimaryKeySelective(TLogFile record);

    int updateByPrimaryKey(TLogFile record);
}