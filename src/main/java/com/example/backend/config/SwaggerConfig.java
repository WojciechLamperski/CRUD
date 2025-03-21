package com.example.backend.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    @Profile("prod") // Only in production
    public GroupedOpenApi getOnlyApi() {
        return GroupedOpenApi.builder()
                .group("get-only")
                .pathsToMatch("/api/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    if (!isGetMethod(handlerMethod)) {
                        return null; // Remove non-GET methods
                    }
                    return operation;
                })
                .build();
    }

    private boolean isGetMethod(org.springframework.web.method.HandlerMethod handlerMethod) {
        List<Class<? extends Annotation>> getAnnotations = Arrays.asList(GetMapping.class, RequestMapping.class);

        for (Class<? extends Annotation> annotation : getAnnotations) {
            if (handlerMethod.getMethod().isAnnotationPresent(annotation)) {
                if (annotation == GetMapping.class) {
                    return true; // It's a @GetMapping
                }
                if (annotation == RequestMapping.class) {
                    RequestMapping requestMapping = handlerMethod.getMethod().getAnnotation(RequestMapping.class);
                    return Arrays.asList(requestMapping.method()).contains(RequestMethod.GET);
                }
            }
        }
        return false;
    }
}
