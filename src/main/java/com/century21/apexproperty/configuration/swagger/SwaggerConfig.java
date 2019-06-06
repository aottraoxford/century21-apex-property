package com.century21.apexproperty.configuration.swagger;

import com.google.common.collect.Lists;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.awt.print.Pageable;
import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .useDefaultResponseMessages(false)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.century21.apexproperty.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(swaggerInfo())
//                .securityContexts(Collections.singletonList(securityContext()))
//                .securitySchemes(Arrays.asList(securitySchema()));
//    }
//    private ApiInfo swaggerInfo() {
//        return new ApiInfo(
//                "Century 21 Apex Property Backend Webservice",
//                "API",
//                "version 1.0",
//                "century21api.herokuapp.com",
//                new Contact("VANN DARONG", "https://www.facebook.com/darongvann44", "darongvann@gmail.com"),
//                "license by darong",
//                "https://www.facebook.com/darongvann44",
//                Collections.emptyList()
//        );
//    }
//
//    @Bean
//    public SecurityConfiguration security() {
//        return SecurityConfigurationBuilder.builder()
//                .clientId("c21c")
//                .clientSecret("12345678@Cenutry21CambodiaRealEstate")
//                .scopeSeparator(" ")
//                .useBasicAuthenticationWithAccessCodeGrant(true)
//                .build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//
//        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
//        authorizationScopes[0] = new AuthorizationScope("read", "read all");
//        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
//        authorizationScopes[2] = new AuthorizationScope("write", "write all");
//
//        return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
//    }
//
//    private OAuth securitySchema() {
//
//        List<AuthorizationScope> authorizationScopeList = CollectionHelper.newArrayList();
//        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
//        authorizationScopeList.add(new AuthorizationScope("write", "access all"));
//
//        List<GrantType> grantTypes = CollectionHelper.newArrayList();
//        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant("/oauth/token");
//        grantTypes.add(passwordCredentialsGrant);
//
//        return new OAuth("oauth2", authorizationScopeList, grantTypes);
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/apis/.*";


    @Bean
    public Docket swaggerSpringfoxDocket() {

        Contact contact = new Contact(
                "Matyas Albert-Nagy",
                "https://justrocket.de",
                "matyas@justrocket.de");

        List<VendorExtension> vext = new ArrayList<>();
        ApiInfo apiInfo = new ApiInfo(
                "Backend API",
                "This is the best stuff since sliced bread - API",
                "6.6.6",
                "https://justrocket.de",
                contact,
                "MIT",
                "https://justrocket.de",
                vext);

//        ParameterBuilder builder = new ParameterBuilder();
//        builder.name("x-auth")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false);

//        ArrayList<Parameter> parameters=new ArrayList<>();
//        parameters.add(builder.build());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .pathMapping("/")
                .apiInfo(ApiInfo.DEFAULT)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false);

        docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.century21.apexproperty.controller"))
                .paths(PathSelectors.regex("/api.*"))
                .build();
                //.globalOperationParameters(parameters);
        return docket;
    }


    private ApiKey apiKey() {
        return new ApiKey("Authorization", AUTHORIZATION_HEADER, "header");
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
}
