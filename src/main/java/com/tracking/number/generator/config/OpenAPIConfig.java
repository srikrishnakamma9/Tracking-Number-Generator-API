package com.tracking.number.generator.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Tracking Number Generator API",
        version = "1.0",
        description = "API for generating tracking numbers"
    )
)
public class OpenAPIConfig {
}
