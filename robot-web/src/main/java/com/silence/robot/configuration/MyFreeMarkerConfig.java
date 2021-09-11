package com.silence.robot.configuration;

import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * freemarker 配置
 *
 * @author silence
 * @date 2021/9/5
 */
@Configuration
public class MyFreeMarkerConfig {

    private final Logger logger = LoggerFactory.getLogger(MyFreeMarkerConfig.class);

    @Value("${robot.ftl.path}")
    private String ftlPath;

    @Bean
    public freemarker.template.Configuration myFreeMarkerConfiguration() {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_29);
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourceUtils.CLASSPATH_URL_PREFIX + "ftl/**/*.ftl");
            for (Resource resource : resources) {
                InputStream inputStream = resource.getInputStream();
                Path path = Paths.get(ftlPath + resource.getFilename());
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }
            cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        } catch (IOException e) {
            logger.error("初始化freemarker配置类失败", e);
            throw new RuntimeException("初始化freemarker配置类失败", e);
        }
        cfg.setDefaultEncoding("UTF-8");
        // 设置错误将如何出现。
        // 在网页*开发* TemplateExceptionHandler.HTML_DEBUG_HANDLER 更好。
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // 不要在 FreeMarker 中记录异常，它无论如何都会向你抛出：
        cfg.setLogTemplateExceptions(false);

        // 将模板处理期间抛出的未经检查的异常包装到 TemplateException-s 中：
        cfg.setWrapUncheckedExceptions(true);

        // 在读取空循环变量时不要回退到更高的范围：
        cfg.setFallbackOnNullLoopVariable(false);
        return cfg;
    }

}
