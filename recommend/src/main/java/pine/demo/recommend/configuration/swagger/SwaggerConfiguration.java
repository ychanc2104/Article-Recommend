//package pine.demo.recommend.configuration.swagger;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
//import springfox.documentation.builders.*;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
//import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@EnableSwagger2
//@Configuration("SwaggerConfiguration")
//public class SwaggerConfiguration {
//    @Value("${swagger.ui.enable:false}")
//    private Boolean isSwaggerUiEnabled;
//    @Value("${git.commit.id:na}")
//    private String gitCommitId;
//
//    @Bean("docket")
//    public Docket docket() {
//        List<RequestParameter> parameterList = new ArrayList<>();
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(this.apiInfo())
//                .enable(this.isSwaggerUiEnabled)
//                .globalRequestParameters(parameterList)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.robinstech.application.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(generateApiKeys())
//                .securityContexts(Collections.singletonList(securityContext()));
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Order API")
//                .version("Since 2.12")
//                .description(description())
//                .build();
//    }
//
//    private String description() {
//        return "Service Code order\n\n" +
//                "半自動驗證：\n" +
//                "1. 使用 login 取得 jwt\n" +
//                "2. 點擊右側 Authorize 按鈕\n" +
//                "3. 將 jwt 填入 value 後，再點擊下方 Authorize 按紐\n" +
//                "4. 在頁面沒有刷新之前，所有 endpoint 都會自動填入 jwt";
//    }
//
//    private List<SecurityScheme> generateApiKeys() {
//        List<SecurityScheme> apiKeys = new ArrayList<>();
//        apiKeys.add(accessTokenApiKey());
//        apiKeys.add(authTokenApiKey());
//        apiKeys.add(serviceCodeApiKey());
//        apiKeys.add(serviceSecretApiKey() );
//
//        return apiKeys;
//    }
//
//    private ApiKey accessTokenApiKey() {
//        return new ApiKey("accessToken", SecurityConfiguration.ACCESS_HEADER, "header");
//    }
//
//    private ApiKey authTokenApiKey() {
//        return new ApiKey("authToken", SecurityConfiguration.AUTH_HEADER, "header");
//    }
//
//    private ApiKey serviceCodeApiKey() {
//        return new ApiKey("serviceCode", SecurityConfiguration.SERVICE_CODE_HEADER, "header");
//    }
//
//
//    private ApiKey serviceSecretApiKey() {
//        return new ApiKey("serviceSecret", SecurityConfiguration.SERVICE_SECRET_HEADER, "header");
//    }
//
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .operationSelector(operationContext -> true)
//                .build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
//        return Arrays.asList(
//                new SecurityReference("accessToken", authorizationScopes),
//                new SecurityReference("authToken", authorizationScopes),
//                new SecurityReference("serviceCode", authorizationScopes),
//                new SecurityReference("serviceSecret", authorizationScopes));
//    }
//
//    // Fix error: Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
//    @Bean
//    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
//        return new BeanPostProcessor() {
//
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
//                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
//                }
//                return bean;
//            }
//
//            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
//                List<T> copy = mappings.stream()
//                        .filter(mapping -> mapping.getPatternParser() == null)
//                        .collect(Collectors.toList());
//                mappings.clear();
//                mappings.addAll(copy);
//            }
//
//            @SuppressWarnings("unchecked")
//            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
//                try {
//                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
//                    field.setAccessible(true);
//                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
//                } catch (IllegalArgumentException | IllegalAccessException e) {
//                    throw new IllegalStateException(e);
//                }
//            }
//        };
//    }
//}