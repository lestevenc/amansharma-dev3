package com.simon.common.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simon on 2017/2/25.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Value("${swagger2.title}")
    private String title;

    @Value("${swagger2.description}")
    private String description;

    @Value("${swagger2.terms-of-service-url}")
    private String termsOfServiceUrl;

    @Value("${swagger2.version}")
    private String version;

    @Value("${swagger2.contact.name}")
    private String contactName;

    @Value("${swagger2.contact.url}")
    private String contactUrl;

    @Value("${swagger2.contact.email}")
    private String contactEmail;

    @Value("${swagger2.license}")
    private String license;

    @Value("${swagger2.license-url}")
    private String licenseUrl;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("^(?!oauth).*$"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .directModelSubstitute(Date.class, String.class)
                .directModelSubstitute(Time.class, String.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .version(version)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> securityReferences = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    private List<ApiKey> securitySchemes(){
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts(){
        List<SecurityContext> securityContextList = new ArrayList<>();
        //????????????"auth"????????????????????????securitySchemes
        securityContextList.add(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("^(?!oauth).*$")).build());
        return securityContextList;
    }
}
