package com.silence.robot.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.member.BusinessInfoDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.mermber.BusinessInfoService;

/**
 *
 * @author silence
 * @date 2021/2/5
 */
@RestController
public class BusinessInfoController {
    @Resource
    private BusinessInfoService businessInfoService;

    @PostMapping("/addBusinessInfo")
    public DataResponse<?> addBusinessInfo(@RequestBody BusinessInfoDto businessInfoDto) {
        businessInfoService.addBusinessInfo(businessInfoDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateBusinessInfo")
    public DataResponse<?> updateBusinessInfo(@RequestBody BusinessInfoDto businessInfoDto){
        businessInfoService.updateBusinessInfo(businessInfoDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteBusinessInfo")
    public DataResponse<?> deleteBusinessInfo(@RequestParam String businessName) {
        businessInfoService.deleteBusinessInfo(businessName);
        return new DataResponse<>();
    }

    @GetMapping("getBusinessInfo")
    public DataResponse<List<BusinessInfoDto>> getBusinessInfo(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<BusinessInfoDto> robotPage = businessInfoService.queryAllBusinessInfo(page, limit);
        DataResponse<List<BusinessInfoDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }

    @GetMapping("/getBusinessInfoByCondition")
    public DataResponse<List<BusinessInfoDto>> getBusinessInfoByCondition(@RequestParam String businessDesc, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<BusinessInfoDto> robotPage = businessInfoService.queryBusinessInfoByBusinessDesc(businessDesc, page, limit);
        DataResponse<List<BusinessInfoDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }
}
