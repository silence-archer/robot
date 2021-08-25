package com.silence.robot.service;


import com.silence.robot.domain.DatabaseInfoDto;
import com.silence.robot.mapper.TDatabaseInfoMapper;
import com.silence.robot.model.TDatabaseInfo;
import com.silence.robot.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        TDatabaseInfo databaseInfo = BeanUtils.copy(TDatabaseInfo.class, databaseInfoDto);
        databaseInfoMapper.insert(databaseInfo);
    }

    public void updateDatabaseInfo(DatabaseInfoDto databaseInfoDto){
        TDatabaseInfo databaseInfo = BeanUtils.copy(TDatabaseInfo.class, databaseInfoDto);
        databaseInfoMapper.updateByBusinessType(databaseInfo);
    }

    public void deleteDatabaseInfo(String id) {
        databaseInfoMapper.deleteByPrimaryKey(id);
    }

    public List<DatabaseInfoDto> queryAllDatabaseInfo() {
        List<TDatabaseInfo> DatabaseInfos = databaseInfoMapper.selectAll();
        return BeanUtils.copyList(DatabaseInfoDto.class, DatabaseInfos);
    }

    public DatabaseInfoDto queryDatabaseInfoByBusinessType(String businessType) {
        return BeanUtils.copy(DatabaseInfoDto.class, databaseInfoMapper.selectByBusinessType(businessType));
    }


}
