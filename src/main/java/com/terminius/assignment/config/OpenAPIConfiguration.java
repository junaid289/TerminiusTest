package com.terminius.assignment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhammad.junaid on 9/3/2021
 */
@Configuration
public class OpenAPIConfiguration {
    private static final String AUTHORIZATION_KEY = "authorization";
    private static final String CONSUMER_KEY = "consumer_id";
    public static final String AUTHORIZATION = "Authorization";
    public static final String DESCRIPTION = "This is jwt authorization token";
    public static final String THIS_IS_HEADER = "This is header";
    public static final String TERMINUS_TEST = "Terminus Programing test";
    public static final String TERMINUS_TEST_ENDPOINTS = "Terminus test EndPoints";
    private final ServletContext context;
    public OpenAPIConfiguration(ServletContext context) {
        this.context = context;
    }
    @Bean
    public OpenAPI customOpenAPI() {
        Map<String, SecurityScheme> securitySchemes =  new HashMap<>();
        securitySchemes.put(AUTHORIZATION_KEY,apiKeySecuritySchema());
        return new OpenAPI()
                .components(new Components()
                        .securitySchemes(securitySchemes)// define the apiKey SecuritySchema
                        .addHeaders(CONSUMER_KEY, getConsumerIdAsHeader()))
                .info(new Info().title(TERMINUS_TEST).description(
                        TERMINUS_TEST_ENDPOINTS))
                .addServersItem(new Server().url(context.getContextPath()))
                .security(Collections.singletonList(new SecurityRequirement().addList(AUTHORIZATION_KEY))); // then apply it. If you don't apply it will not be added to the header in cURL
    }
    private Header getConsumerIdAsHeader() {
        return new Header()
                .description(THIS_IS_HEADER)
                .schema(new StringSchema());
    }
    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name(AUTHORIZATION) // authorisation-token
                .description(DESCRIPTION)
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY);
    }
}
