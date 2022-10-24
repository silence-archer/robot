package com.silence.robot.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class FreeMarkerUtil {

    @Resource
    private Configuration myFreeMarkerConfiguration;

    private final Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);
    public void getFreeMarker(String ftl, String filename, Object freeMarkerDto) {
        try {
            Template template = myFreeMarkerConfiguration.getTemplate(ftl);
            Writer out = new OutputStreamWriter(Files.newOutputStream(Paths.get(filename)));
            template.process(freeMarkerDto, out);
            out.flush();
            out.close();
        } catch (IOException | TemplateException e) {
            logger.error("获取freemarker模板文件失败", e);
            throw new RuntimeException("获取freemarker模板文件失败");
        }
    }
}
