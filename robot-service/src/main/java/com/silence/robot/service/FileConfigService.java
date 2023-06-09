package com.silence.robot.service;

import com.silence.robot.domain.FileConfigDto;
import com.silence.robot.mapper.TFileConfigMapper;
import com.silence.robot.model.TFileConfig;
import com.silence.robot.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件配置类操作
 *
 * @author silence
 * @since 2021/2/3
 */
@Service
public class FileConfigService {
    private final Logger logger = LoggerFactory.getLogger(FileConfigService.class);

    @Resource
    private TFileConfigMapper fileConfigMapper;

    public void addFileConfig(FileConfigDto fileConfigDto) {
        TFileConfig fileConfig = BeanUtils.copy(TFileConfig.class, fileConfigDto);
        fileConfigMapper.insert(fileConfig);
    }

    public void updateFileConfig(FileConfigDto fileConfigDto){
        TFileConfig fileConfig = BeanUtils.copy(TFileConfig.class, fileConfigDto);
        fileConfigMapper.updateByPrimaryKey(fileConfig);
    }

    public void deleteFileConfig(String id) {
        fileConfigMapper.deleteByPrimaryKey(id);
    }

    public List<FileConfigDto> queryAllFileConfig() {
        List<TFileConfig> fileConfigs = fileConfigMapper.selectAll();
        return BeanUtils.copyList(FileConfigDto.class, fileConfigs);
    }

    public FileConfigDto queryFileConfigById(String id) {
        return BeanUtils.copy(FileConfigDto.class, fileConfigMapper.selectByPrimaryKey(id));
    }


}
