package com.silence.robot.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.member.BusinessInfoDto;
import com.silence.robot.domain.member.MerchantInfoDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.mermber.MerchantInfoService;

/**
 * 文件配置处理
 *
 * @author silence
 * @date 2021/2/5
 */
@RestController
public class MerchantInfoController {
    @Resource
    private MerchantInfoService merchantInfoService;

    @PostMapping("/addMerchantInfo")
    public DataResponse<?> addMerchantInfo(@RequestBody MerchantInfoDto merchantInfoDto) {
        merchantInfoService.addMerchantInfo(merchantInfoDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateMerchantInfo")
    public DataResponse<?> updateMerchantInfo(@RequestBody MerchantInfoDto merchantInfoDto){
        merchantInfoService.updateMerchantInfo(merchantInfoDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteMerchantInfo")
    public DataResponse<?> deleteMerchantInfo(@RequestParam String merchantName) {
        merchantInfoService.deleteMerchantInfo(merchantName);
        return new DataResponse<>();
    }

    @GetMapping("getMerchantInfo")
    public DataResponse<List<MerchantInfoDto>> getMerchantInfo(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<MerchantInfoDto> robotPage = merchantInfoService.queryAllMerchantInfo(page, limit);
        DataResponse<List<MerchantInfoDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }

    @GetMapping("/getMerchantInfoByCondition")
    public DataResponse<List<MerchantInfoDto>> getMerchantInfoByCondition(@RequestParam String merchantDesc, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<MerchantInfoDto> robotPage = merchantInfoService.queryByCondition(merchantDesc, page, limit);
        DataResponse<List<MerchantInfoDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }
}
