package com.silence.robot.controller;

import com.silence.robot.domain.DatabaseInfoDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.DatabaseInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author silence
 * @since 2021/6/27
 */
@RestController
public class DatabaseInfoController {
    @Resource
    private DatabaseInfoService databaseInfoService;

    @PostMapping("/addDatabaseInfo")
    public DataResponse<?> addDatabaseInfo(@RequestBody DatabaseInfoDto databaseInfoDto) {
        databaseInfoService.addDatabaseInfo(databaseInfoDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateDatabaseInfo")
    public DataResponse<?> updateDatabaseInfo(@RequestBody DatabaseInfoDto databaseInfoDto){
        databaseInfoService.updateDatabaseInfo(databaseInfoDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteDatabaseInfo")
    public DataResponse<?> deleteDatabaseInfo(@RequestParam String businessType) {
        databaseInfoService.deleteDatabaseInfo(businessType);
        return new DataResponse<>();
    }

    @GetMapping("getDatabaseInfo")
    public DataResponse<List<DatabaseInfoDto>> getDatabaseInfo(@RequestParam Integer page, @RequestParam Integer limit) {
        RobotPage<DatabaseInfoDto> robotPage = databaseInfoService.queryAllDatabaseInfo(page, limit);
        DataResponse<List<DatabaseInfoDto>> dataResponse = new DataResponse<>(robotPage.getList());
        dataResponse.setCount(robotPage.getTotal());
        return dataResponse;
    }

    @GetMapping("/getDatabaseInfoByCondition")
    public DataResponse<DatabaseInfoDto> getDatabaseInfoByCondition(@RequestParam String businessType) {
        DatabaseInfoDto databaseInfoDto = databaseInfoService.queryDatabaseInfoByBusinessType(businessType);
        return new DataResponse<>(databaseInfoDto);
    }
}
