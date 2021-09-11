package com.silence.robot.mapper;

import com.silence.robot.model.TInterfaceScene;

import java.util.List;

public interface TInterfaceSceneMapper {
    int deleteByPrimaryKey(String id);
    int deleteBatchByPrimaryKey(List<String> ids);

    int insert(TInterfaceScene record);

    int insertSelective(TInterfaceScene record);

    TInterfaceScene selectByPrimaryKey(String id);
    List<TInterfaceScene> selectByTranCode(String tranCode);
    TInterfaceScene selectBySceneId(String sceneId);

    int updateByPrimaryKeySelective(TInterfaceScene record);

    int updateByPrimaryKey(TInterfaceScene record);

    List<TInterfaceScene> selectAll();
}