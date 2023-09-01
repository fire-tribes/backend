package com.fires.fires.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "fires", version = "v1")
)
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public class SwaggerConfig {


    @Bean
    public GroupedOpenApi openApi(OpenApiCustomizer jwtAuthFilterApiCustomizer) {
        String[] paths = {"/api/**"};

        return GroupedOpenApi.builder()
                             .group("fires api v1")
                             .pathsToMatch(paths)
                             .addOpenApiCustomizer(jwtAuthFilterApiCustomizer)
                             .build();
    }
}
