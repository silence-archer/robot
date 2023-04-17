package com.silence.robot.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.DatabaseInfoDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.mapper.TDatabaseInfoMapper;
import com.silence.robot.model.TDatabaseInfo;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import io.lettuce.core.RedisClient;

/**
 * 数据库配置类操作
 *
 * @author silence
 * @date 2021/2/3
 */
@Service
public class DatabaseInfoService {
    private final Logger logger = LoggerFactory.getLogger(DatabaseInfoService.class);

    @Resource
    private TDatabaseInfoMapper databaseInfoMapper;

    public void addDatabaseInfo(DatabaseInfoDto databaseInfoDto) {
        if (databaseInfoDto.getType().equals("redis")) {
            RedisClient redisClient = RedisClient.create(databaseInfoDto.getUrl());
            redisClient.shutdown();
        }else {
            CommonUtils.getResultSetByDataBase(databaseInfoDto.getType(), "select 1 from dual", databaseInfoDto.getUrl(), databaseInfoDto.getUser(), databaseInfoDto.getPassword());
        }
        TDatabaseInfo databaseInfo = BeanUtils.copy(TDatabaseInfo.class, databaseInfoDto);
        databaseInfoMapper.insert(databaseInfo);
    }

    public void updateDatabaseInfo(DatabaseInfoDto databaseInfoDto){
        CommonUtils.getResultSetByDataBase(databaseInfoDto.getType(), "select 1 from dual", databaseInfoDto.getUrl(), databaseInfoDto.getUser(), databaseInfoDto.getPassword());
        TDatabaseInfo databaseInfo = BeanUtils.copy(TDatabaseInfo.class, databaseInfoDto);
        databaseInfoMapper.updateByBusinessType(databaseInfo);
    }

    public void deleteDatabaseInfo(String businessType) {
        databaseInfoMapper.deleteByBusinessType(businessType);
    }

    public RobotPage<DatabaseInfoDto> queryAllDatabaseInfo(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TDatabaseInfo> databaseInfos = databaseInfoMapper.selectAll();
        PageInfo<TDatabaseInfo> pageInfo = new PageInfo<>(databaseInfos);
        List<DatabaseInfoDto> databaseInfoDtos = BeanUtils.copyList(DatabaseInfoDto.class, pageInfo.getList());
        return new RobotPage<>(pageInfo.getTotal(), databaseInfoDtos);
    }

    public DatabaseInfoDto queryDatabaseInfoByBusinessType(String businessType) {
        return BeanUtils.copy(DatabaseInfoDto.class, databaseInfoMapper.selectByBusinessType(businessType));
    }


}
