package com.silence.robot.mapper;

import com.silence.robot.model.TMockInfo;
import com.silence.robot.model.TUser;

import java.util.List;

public interface TMockInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(TMockInfo record);

    TMockInfo selectByPrimaryKey(String id);
    List<TMockInfo> selectByMockUrl(String mockUrl);

    List<TMockInfo> selectAll();

    int updateByPrimaryKey(TMockInfo record);


}