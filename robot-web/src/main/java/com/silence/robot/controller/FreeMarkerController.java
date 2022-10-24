package com.silence.robot.controller;

import com.silence.robot.domain.AutoInterfaceDto;
import com.silence.robot.domain.freemarker.FreeMarkerArrayDto;
import com.silence.robot.domain.freemarker.FreeMarkerDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.enumeration.AutoInterfaceEnum;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.service.FreeMarkerService;
import com.silence.robot.service.SubscribeConfigInfoService;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FreeMarkerUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/5
 */
@RestController
public class FreeMarkerController {
    private final Logger logger = LoggerFactory.getLogger(FreeMarkerController.class);

    @Resource
    private FreeMarkerService freeMarkerService;
    @Resource
    private FreeMarkerUtil freeMarkerUtil;
    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;

    @PostMapping("/autoInterface")
    public DataResponse<?> getInterfaceFreeMarker(@RequestBody AutoInterfaceDto autoInterfaceDto) {
        FreeMarkerDto inputFreeMarkerDto = freeMarkerService.getInterfaceFreeMarker(autoInterfaceDto.getUri(), autoInterfaceDto.getTranCode(), autoInterfaceDto.getPort(), autoInterfaceDto.getInterfaceInput());
        FreeMarkerDto outputFreeMarkerDto = freeMarkerService.getInterfaceFreeMarker(autoInterfaceDto.getUri(), autoInterfaceDto.getTranCode(), autoInterfaceDto.getPort(), autoInterfaceDto.getInterfaceOutput());
        String freeMarkerVersion = subscribeConfigInfoService.getConfigValue(ConfigEnum.FREE_MARKER_VERSION_ENUM);
        if (CommonUtils.isEquals("2.0", freeMarkerVersion)) {
            getInterfaceVersion2(inputFreeMarkerDto, outputFreeMarkerDto, autoInterfaceDto);
        }else {
            getInterface(inputFreeMarkerDto, outputFreeMarkerDto, autoInterfaceDto);
        }
        freeMarkerService.autoUpdateMenu(autoInterfaceDto.getTranCode(), autoInterfaceDto.getTranName());
        return new DataResponse<>();
    }

    private void getInterface(FreeMarkerDto inputFreeMarkerDto, FreeMarkerDto outputFreeMarkerDto, AutoInterfaceDto autoInterfaceDto) {
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_HTML.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_HTML), inputFreeMarkerDto);
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_JS.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_JS), inputFreeMarkerDto);
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_RESULT_HTML.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_RESULT_HTML), outputFreeMarkerDto);
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_RESULT_JS.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_RESULT_JS), outputFreeMarkerDto);

    }

    private void getInterfaceVersion2(FreeMarkerDto inputFreeMarkerDto, FreeMarkerDto outputFreeMarkerDto, AutoInterfaceDto autoInterfaceDto) {
        updateInterfaceArray(inputFreeMarkerDto.getArrays());
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_2_0_HTML.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_HTML), inputFreeMarkerDto);
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_2_0_JS.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_JS), inputFreeMarkerDto);
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_2_0_RESULT_HTML.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_RESULT_HTML), outputFreeMarkerDto);
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_2_0_RESULT_JS.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_RESULT_JS), outputFreeMarkerDto);
    }

    private void updateInterfaceArray(List<FreeMarkerArrayDto> freeMarkerArrayDtos) {
        freeMarkerArrayDtos.forEach(freeMarkerArrayDto -> freeMarkerArrayDto.getList().forEach(freeMarkerBodyDto -> freeMarkerBodyDto.setName(freeMarkerArrayDto.getName()+"$"+freeMarkerBodyDto.getName())));
    }


}
