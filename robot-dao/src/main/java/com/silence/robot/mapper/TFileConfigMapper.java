package com.silence.robot.mapper;

import com.silence.robot.model.TFileConfig;

public interface TFileConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(TFileConfig record);

    int insertSelective(TFileConfig record);

    TFileConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TFileConfig record);

    int updateByPrimaryKey(TFileConfig record);
}