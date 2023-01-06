package com.silence.robot.mapper;

import com.silence.robot.model.TFileConfig;

import java.util.List;

public interface TFileConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(TFileConfig record);

    int insertSelective(TFileConfig record);

    TFileConfig selectByPrimaryKey(String id);
    TFileConfig selectByBusinessType(String businessType);
    List<TFileConfig> selectAll();

    int updateByPrimaryKeySelective(TFileConfig record);

    int updateByPrimaryKey(TFileConfig record);
}