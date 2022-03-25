package com.leesh.springbootjwttutorial.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //swagger 대상 패키지 설정
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.leesh.springbootjwttutorial.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JWT인증,주문API")
                .description("JWT인증,주문API 입니다.")
                .version("1.0.0")
                .termsOfServiceUrl("")
                .license("LICENSE")
                .licenseUrl("")
                .build();
    }
    // 완료가 되었으면 URL 로 접속 => http://localhost:8080/swagger-ui.html
}
