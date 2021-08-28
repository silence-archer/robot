package com.silence.robot.controller;

import com.silence.robot.constants.RobotConstants;
import com.silence.robot.domain.DataDiffDetailDto;
import com.silence.robot.domain.DataDiffDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.DatabaseOperationService;
import com.silence.robot.utils.CommonUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author silence
 * @date 2021/6/27
 */
@RestController
public class DatabaseOperationController {

    @Resource
    private DatabaseOperationService databaseOperationService;

    @GetMapping("/getDiff")
    public DataResponse<List<DataDiffDto>> getDiff(@RequestParam String origBusinessType, @RequestParam String destBusinessType, @RequestParam String dataName, @RequestParam Integer page, @RequestParam Integer limit) {
        List<DataDiffDto> list;
        if (RobotConstants.PARAMETER_TABLE.equals(dataName)) {
            list = databaseOperationService.getDiffParams(origBusinessType, destBusinessType);
        }else {
            list = databaseOperationService.getDiffData(destBusinessType);
        }
        RobotPage<DataDiffDto> robotPage = CommonUtils.getSubList(list, page, limit);
        DataResponse<List<DataDiffDto>> dataResponse = new DataResponse<>(robotPage.getList());
        dataResponse.setCount(robotPage.getTotal());
        return dataResponse;
    }

    @PostMapping("/getDiffDetail")
    public DataResponse<List<DataDiffDetailDto>> getDiffDetail(@RequestBody DataDiffDto dataDiffDto) {
        List<DataDiffDetailDto> list = databaseOperationService.getDiffDetail(dataDiffDto);
        int page = dataDiffDto.getPage() == null ? 1 : dataDiffDto.getPage();
        int limit = dataDiffDto.getLimit() == null ? Integer.MAX_VALUE : dataDiffDto.getLimit();
        RobotPage<DataDiffDetailDto> robotPage = CommonUtils.getSubList(list, page, limit);
        DataResponse<List<DataDiffDetailDto>> dataResponse = new DataResponse<>(robotPage.getList());
        dataResponse.setCount(robotPage.getTotal());
        return dataResponse;
    }


}
