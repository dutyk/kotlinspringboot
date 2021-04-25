package io.kang.example.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.kang.example.controller.swagger"))
                .paths(PathSelectors.any())
                .build()
    }

    @Bean
    fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("使用Swagger2构建Restful APIs")
                .contact(Contact("dutyk", "https://github.com/dutyk", "1013812851@qq.com"))
                .version("1.0")
                .description("Demo for book Kotlin Springboot Action")
                .build()
    }
}