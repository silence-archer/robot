package com.silence.robot.controller;

import com.silence.robot.domain.FileConfigDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.FileConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件配置处理
 *
 * @author silence
 * @date 2021/2/5
 */
@RestController
public class FileConfigController {
    @Resource
    private FileConfigService fileConfigService;

    @PostMapping("/addFileConfig")
    public DataResponse<?> addFileConfig(@RequestBody FileConfigDto fileConfigDto) {
        fileConfigService.addFileConfig(fileConfigDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateFileConfig")
    public DataResponse<?> updateFileConfig(@RequestBody FileConfigDto fileConfigDto){
        fileConfigService.updateFileConfig(fileConfigDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteFileConfig")
    public DataResponse<?> deleteFileConfig(@RequestParam String id) {
        fileConfigService.deleteFileConfig(id);
        return new DataResponse<>();
    }

    @GetMapping("queryAllFileConfig")
    public DataResponse<List<FileConfigDto>> queryAllFileConfig() {
        List<FileConfigDto> fileConfigDtos = fileConfigService.queryAllFileConfig();
        return new DataResponse<>(fileConfigDtos);
    }

    @GetMapping("/queryFileConfigById")
    public DataResponse<FileConfigDto> queryFileConfigById(@RequestParam String id) {
        FileConfigDto fileConfigDto = fileConfigService.queryFileConfigById(id);
        return new DataResponse<>(fileConfigDto);
    }
}
