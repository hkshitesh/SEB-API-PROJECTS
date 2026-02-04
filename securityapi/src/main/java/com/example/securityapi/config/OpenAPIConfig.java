package com.example.securityapi.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI custom() {
        return new OpenAPI()
                .info(new Info()
                        .title("Security API Lab")
                        .version("1.0")
                        .description("OWASP API Top 10 Demonstration"));
    }
}
