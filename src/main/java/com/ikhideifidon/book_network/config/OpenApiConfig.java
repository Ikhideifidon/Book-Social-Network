package com.ikhideifidon.book_network.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info =@Info(
                contact = @Contact(
                        name = "Ifidon Ikhide",
                        email = "ikhideifidon@yahoo.com",
                        url = "https://ikhideifidon.com/rhapsody"
                ),
                description = "OpenApi Documentation for Book Social Network",
                title = "openApi Specification",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of Service"

        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/api/v1"
                ),
                @Server(
                       description = "PROD ENV",
                        url = "https://ikhideifidon.com/rhapsody"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth Description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
