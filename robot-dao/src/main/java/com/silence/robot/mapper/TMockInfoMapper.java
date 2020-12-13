package com.silence.robot.mapper;

import com.silence.robot.model.TMockInfo;
import com.silence.robot.model.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMockInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(TMockInfo record);

    TMockInfo selectByPrimaryKey(String id);
    List<TMockInfo> selectByMockUrlAndModule(String mockUrl, String mockModule);

    List<TMockInfo> selectAll();
    List<TMockInfo> selectByCondition(@Param("mockName") String mockName, @Param("mockInput") String mockInput, @Param("mockUrl") String mockUrl, @Param("mockModule") String mockModule);

    int updateByPrimaryKey(TMockInfo record);


}