package com.silence.robot.mapper;

import java.util.List;

import com.silence.robot.model.TDataDict;
import com.silence.robot.model.TDingClockInfo;


public interface TDingClockInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(TDingClockInfo record);

    int insertSelective(TDingClockInfo record);

    TDingClockInfo selectByPrimaryKey(String id);

    List<TDingClockInfo> selectAll();
    List<TDingClockInfo> selectByItcode(String itcode);
    List<TDingClockInfo> selectByStatus(String status);

    int updateByPrimaryKeySelective(TDingClockInfo record);

    int updateByPrimaryKey(TDingClockInfo record);

}
