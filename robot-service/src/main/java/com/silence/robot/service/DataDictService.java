package com.silence.robot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.DataDictDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.enumeration.FileFormatEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TDataDictMapper;
import com.silence.robot.model.TDataDict;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典
 *
 * @author silence
 * @since 2021/5/4
 */
@Service
public class DataDictService {

    private final Logger logger = LoggerFactory.getLogger(DataDictService.class);

    @Resource
    private TDataDictMapper dataDictMapper;
    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;

    public RobotPage<DataDictDto> getDataDictList(String name, Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        name = CommonUtils.underlineToHump(name);
        List<TDataDict> dataDicts = dataDictMapper.selectByName(name);
        PageInfo<TDataDict> pageInfo = new PageInfo<>(dataDicts);
        List<DataDictDto> dataDictDtos = BeanUtils.copyList(DataDictDto.class, dataDicts);
        return new RobotPage<>(pageInfo.getTotal(), dataDictDtos);
    }

    public RobotPage<DataDictDto> getDataDictList(String name) {
        return getDataDictList(name, 1, 1);
    }

    public void addDataDict(DataDictDto dataDictDto) {
        dataDictMapper.insert(BeanUtils.copy(TDataDict.class, dataDictDto));
    }

    public void updateDataDict(DataDictDto dataDictDto) {
        dataDictMapper.updateByPrimaryKey(BeanUtils.copy(TDataDict.class, dataDictDto));
    }

    public void deleteDataDict(String id) {
        dataDictMapper.deleteByPrimaryKey(id);
    }

    /**
     * TODO
     * @author silence
     * @since 2021/8/2 10:25
     * @param path 文件名称
     */
    public void addDataDictFile(Path path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            logger.error("读取文件失败", e);
            throw new BusinessException(ExceptionCode.FILE_READ_ERROR);
        }
        List<DataDictDto> dataDictDtos = new ArrayList<>(lines.size());
        lines.forEach(line -> {
            DataDictDto dataDictDto = BeanUtils.stringToObject(line, DataDictDto.class, FileFormatEnum.VERTICAL_SEPARATOR);
            if (CommonUtils.existEmpty(dataDictDto.getName(), dataDictDto.getDesc(), dataDictDto.getEnumDesc(), dataDictDto.getEnumName())) {
                throw new BusinessException(ExceptionCode.EMPTY_ERROR);
            }
            dataDictDtos.add(dataDictDto);
        });
        dataDictMapper.insertAndBatch(BeanUtils.copyList(TDataDict.class, dataDictDtos, "id"));

    }

    public Path uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = subscribeConfigInfoService.getConfigValue(ConfigEnum.UPLOAD_PATH_ENUM);
        Path dest = Paths.get(filePath + fileName);
        try {
            Files.createDirectories(Paths.get(filePath));
            file.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败", e);
            throw new BusinessException(ExceptionCode.UPLOAD_ERROR);
        }
        return dest;
    }
}
