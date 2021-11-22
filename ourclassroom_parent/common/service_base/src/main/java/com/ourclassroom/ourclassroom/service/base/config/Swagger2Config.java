package com.ourclassroom.ourclassroom.service.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()

                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();

    }

    @Bean
    public Docket adminApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()

                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();

    }

    @Bean
    public Docket loginApiConfig() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("loginApi")
                .apiInfo(loginApiInfo())
                .select()

                .paths(Predicates.and(PathSelectors.regex("/user/.*")))
                .build();
    }

    private ApiInfo loginApiInfo() {
        return new ApiInfoBuilder()
                .title("Website API Documentation")
                .description("admin api")
                .version("1.0")
                .contact(new Contact("Yifan He", null, "he.yifan2@northeastern.edu"))
                .build();
    }


    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("Website API Documentation")
                .description("web api")
                .version("1.0")
                .contact(new Contact("Yifan He", null, "he.yifan2@northeastern.edu"))
                .build();
    }

    private ApiInfo adminApiInfo(){

        return new ApiInfoBuilder()
                .title("Website API Documentation")
                .description("backend management system api")
                .version("1.0")
                .contact(new Contact("Yifan He", null, "he.yifan2@northeastern.edu"))
                .build();
    }
}
