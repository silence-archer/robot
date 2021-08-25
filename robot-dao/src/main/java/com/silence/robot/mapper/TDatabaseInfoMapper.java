package com.silence.robot.mapper;

import com.silence.robot.model.TDatabaseInfo;

import java.util.List;

public interface TDatabaseInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(TDatabaseInfo record);

    int insertSelective(TDatabaseInfo record);

    TDatabaseInfo selectByPrimaryKey(String id);
    TDatabaseInfo selectByBusinessType(String businessType);

    int updateByPrimaryKeySelective(TDatabaseInfo record);

    int updateByPrimaryKey(TDatabaseInfo record);
    int updateByBusinessType(TDatabaseInfo record);

    List<TDatabaseInfo> selectAll();
}