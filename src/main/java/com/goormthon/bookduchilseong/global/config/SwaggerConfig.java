package com.goormthon.bookduchilseong.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.openapi("3.0.0")
			.components(new Components().addSecuritySchemes("bearerAuth", createBearerAuth()))
			.info(apiInfo())
			.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
	}

	private SecurityScheme createBearerAuth() {
		return new SecurityScheme()
			.name("Authorization")
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT");
	}

	private Info apiInfo() {
		return new Info()
			.title("북두칠성 API")
			.description("북두칠성 API 설명")
			.version("1.0.0");
	}
}