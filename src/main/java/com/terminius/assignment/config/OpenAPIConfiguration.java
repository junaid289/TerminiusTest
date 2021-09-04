package com.terminius.assignment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;

/**
 * Created by muhammad.junaid on 9/3/2021
 */
@Configuration
public class OpenAPIConfiguration {
    public static final String THIS_IS_HEADER = "This is header";
    public static final String TERMINUS_TEST = "Terminus Programing test";
    public static final String TERMINUS_TEST_ENDPOINTS = "Terminus test EndPoints";
    private final ServletContext context;
    public OpenAPIConfiguration(ServletContext context) {
        this.context = context;
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(TERMINUS_TEST).description(
                        TERMINUS_TEST_ENDPOINTS));
    }
}
