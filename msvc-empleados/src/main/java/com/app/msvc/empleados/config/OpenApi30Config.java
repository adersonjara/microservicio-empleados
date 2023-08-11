package com.app.msvc.empleados.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@Configuration
@OpenAPIDefinition(
		info = @Info(title = "MICROSERVICIO - EMPLEADOS", 
		version = "1.1",
		contact = @Contact(name = "Aderson Jara", email = "adersonjara15@gmail.com")),
		security = {@SecurityRequirement(name = "bearerToken")}
)
@SecuritySchemes({
    @SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
})
public class OpenApi30Config {

}
