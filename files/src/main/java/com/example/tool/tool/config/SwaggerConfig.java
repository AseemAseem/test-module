package com.example.tool.tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("APi接口文档")
                .description("")
                .termsOfServiceUrl("https://swagger.io/docs/specification/what-is-swagger/")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("主类")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.tool.tool.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket createWebUploadApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("文件上传")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.tool.tool.fileupload.controller"))
                .build()
                .apiInfo(apiInfo());
    }


}
