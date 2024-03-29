package com.silence.robot.controller;

import com.silence.robot.domain.EurekaManageDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.EurekaManageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * eureka管理器
 *
 * @author silence
 * @since 2020/12/12
 */
@RestController
public class EurekaManageController {

    @Resource
    private EurekaManageService eurekaManageService;

    @GetMapping("/getEurekaInfo")
    public DataResponse<List<EurekaManageDto>> getEurekaInfo(@RequestParam("eurekaUrl") String eurekaUrl) {
        List<EurekaManageDto> eurekaInstanceList = eurekaManageService.getEurekaInstanceList(eurekaUrl);
        return new DataResponse<>(eurekaInstanceList);
    }
}
