package com.silence.robot.controller;

import com.silence.robot.domain.LogFileDto;
import com.silence.robot.domain.QueryLogFileDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.LogFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志查看
 *
 * @author silence
 * @date 2020/10/7
 */
@RestController
public class LogFileController {

    @Resource
    private LogFileService logFileService;

    @PostMapping("/getLogFileByCondition")
    public DataResponse<List<LogFileDto>> getLogFileByCondition(@RequestBody QueryLogFileDto logFileDto) {

        RobotPage<LogFileDto> logFileDtoRobotPage = logFileService.getLogFileByCondition(logFileDto, logFileDto.getPage(), logFileDto.getLimit());

        DataResponse<List<LogFileDto>> dataResponse = new DataResponse<>(logFileDtoRobotPage.getList());
        dataResponse.setCount(logFileDtoRobotPage.getTotal());
        return dataResponse;
    }

    @GetMapping("/getLogFileInfo")
    public DataResponse<List<LogFileDto>> getLogFileInfo(@RequestParam Integer page, @RequestParam Integer limit) {

        RobotPage<LogFileDto> logFileDtoRobotPage = logFileService.getLogFileByCondition(new QueryLogFileDto(), page, limit);

        DataResponse<List<LogFileDto>> dataResponse = new DataResponse<>(logFileDtoRobotPage.getList());
        dataResponse.setCount(logFileDtoRobotPage.getTotal());
        return dataResponse;
    }

    @PostMapping("/deleteLogFileByCondition")
    public DataResponse<?> deleteLogFileByCondition(@RequestBody QueryLogFileDto logFileDto) {

        logFileService.deleteLogFileByCondition(logFileDto);

        return new DataResponse<>();
    }


}
