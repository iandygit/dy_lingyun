package com.lingyun.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class Swagger2 extends AbstractSwagger2  {

    @Bean
    public Docket webSiteApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(this.buildParameter())
                //.directModelSubstitute(Region.class, Integer.class)
                .groupName("授权中心")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lingyun.auth"))
                //.paths(PathSelectors.ant("/com/magic/controller/product/**"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        Contact contact = new Contact("综合信息管理系统APIs.", "http://www.51gkp.com", "magic_sys@lasergroup.cn");
        return new ApiInfoBuilder()
                .title("综合信息管理系统 API")
                .description("授权中心api")
                //.termsOfServiceUrl("https://www.51gkp.com")
                .licenseUrl("https://company.51gkp.com")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
  /*  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");

    }*/

}
