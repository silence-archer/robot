package com.silence.robot.controller;

import com.silence.robot.domain.DataDictDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.DataDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典controller
 *
 * @author silence
 * @date 2021/5/4
 */
@RestController
public class DataDictController {

    private final Logger logger = LoggerFactory.getLogger(DataDictController.class);

    @Resource
    private DataDictService dataDictService;

    @GetMapping("/getDataDictList")
    public DataResponse<List<DataDictDto>> getDataDictList(@RequestParam Integer page, @RequestParam Integer limit) {
        RobotPage<DataDictDto> dataDictList = dataDictService.getDataDictList(null, page, limit);
        DataResponse<List<DataDictDto>> dataResponse = new DataResponse<>(dataDictList.getList());
        dataResponse.setCount(dataDictList.getTotal());
        return dataResponse;
    }

    @GetMapping("/getDataDictByName")
    public DataResponse<List<DataDictDto>> getDataDictByName(@RequestParam String name, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<DataDictDto> dataDictList = dataDictService.getDataDictList(name, page, limit);
        DataResponse<List<DataDictDto>> dataResponse = new DataResponse<>(dataDictList.getList());
        dataResponse.setCount(dataDictList.getTotal());
        return dataResponse;
    }

    @PostMapping("/addDataDict")
    public DataResponse<?> addDataDict(@RequestBody DataDictDto dataDictDto) {
        dataDictService.addDataDict(dataDictDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateDataDict")
    public DataResponse<?> updateDataDict(@RequestBody DataDictDto dataDictDto) {
        dataDictService.updateDataDict(dataDictDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteDataDict")
    @RequiresPermissions("DICT:DELETE")
    public DataResponse<?> deleteDataDict(@RequestParam String id) {
        dataDictService.deleteDataDict(id);
        return new DataResponse<>();
    }

    @PostMapping("upload/uploadDataDict")
    public DataResponse<?> uploadDataDict(@RequestParam("file") MultipartFile file) {
        dataDictService.addDataDictFile(dataDictService.uploadFile(file));
        return new DataResponse<>();
    }
}
