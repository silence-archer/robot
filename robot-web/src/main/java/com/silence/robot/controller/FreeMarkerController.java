package com.silence.robot.controller;

import com.silence.robot.domain.AutoInterfaceDto;
import com.silence.robot.domain.freemarker.FreeMarkerDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.enumeration.AutoInterfaceEnum;
import com.silence.robot.service.FreeMarkerService;
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
    private Configuration myFreeMarkerConfiguration;

    @PostMapping("/autoInterface")
    public DataResponse<?> getInterfaceFreeMarker(@RequestBody AutoInterfaceDto autoInterfaceDto) {
        FreeMarkerDto inputFreeMarkerDto = freeMarkerService.getInterfaceFreeMarker(autoInterfaceDto.getUri(), autoInterfaceDto.getTranCode(), autoInterfaceDto.getPort(), autoInterfaceDto.getInterfaceInput());
        FreeMarkerDto outputFreeMarkerDto = freeMarkerService.getInterfaceFreeMarker(autoInterfaceDto.getUri(), autoInterfaceDto.getTranCode(), autoInterfaceDto.getPort(), autoInterfaceDto.getInterfaceOutput());
        getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_HTML.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_HTML), inputFreeMarkerDto);
        getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_JS.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_JS), inputFreeMarkerDto);
        getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_RESULT_HTML.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_RESULT_HTML), outputFreeMarkerDto);
        getFreeMarker(AutoInterfaceEnum.AUTO_INTERFACE_RESULT_JS.getValue(), freeMarkerService.getFileName(autoInterfaceDto.getTranCode(), AutoInterfaceEnum.AUTO_INTERFACE_RESULT_JS), outputFreeMarkerDto);
        freeMarkerService.autoUpdateMenu(autoInterfaceDto.getTranCode(), autoInterfaceDto.getTranName());
        return new DataResponse<>();
    }

    private void getFreeMarker(String ftl, String filename, FreeMarkerDto freeMarkerDto) {
        try {
            Template template = myFreeMarkerConfiguration.getTemplate(ftl);
            Writer out = new OutputStreamWriter(new FileOutputStream(filename));
            template.process(freeMarkerDto, out);
            out.flush();
            out.close();
        } catch (IOException | TemplateException e) {
            logger.error("获取freemarker模板文件失败", e);
            throw new RuntimeException("获取freemarker模板文件失败");
        }
    }
}
