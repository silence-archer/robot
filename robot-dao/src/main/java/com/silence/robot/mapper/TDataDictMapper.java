package com.silence.robot.mapper;

import com.silence.robot.model.TDataDict;

import java.util.List;

public interface TDataDictMapper {
    int deleteByPrimaryKey(String id);

    int insert(TDataDict record);
    int insertAndBatch(List<TDataDict> records);

    int insertSelective(TDataDict record);

    TDataDict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TDataDict record);

    int updateByPrimaryKey(TDataDict record);

    List<TDataDict> selectByName(String name);
}