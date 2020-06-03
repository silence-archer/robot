/**
 * @title SwaggerConfiguration.java
 * @author oe_machaohui
 * @date 2020/4/8 20:25
 * @copyright 2020 XXX有限公司版权所有
 */
package com.silence.robot.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author oe_machaohui
 * swagger配置类
 * @date 2020/4/8 20:25
 */
@EnableSwagger2
@Configuration
@ConditionalOnProperty(prefix = "silence",name = "swagger.enable",havingValue = "true")
public class SwaggerConfiguration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.silence.robot.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     * @date 2020/4/8 20:32
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("接口文档")
                //创建人
                .contact(new Contact("silence", "", "1048037315@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }
}
