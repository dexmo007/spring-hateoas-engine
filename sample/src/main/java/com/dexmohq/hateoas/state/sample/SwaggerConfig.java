package com.dexmohq.hateoas.state.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Task API")
                        .description("Managing tasks")
                        .contact(new Contact("dexmo", "https://github.com/dexmo007", null))
                        .version("0.0.1-SNAPSHOT")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SampleApplication.class.getPackageName()))
                .paths(PathSelectors.any())
                .build();
    }

}
