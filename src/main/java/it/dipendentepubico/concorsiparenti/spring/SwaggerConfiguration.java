package it.dipendentepubico.concorsiparenti.spring;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public static final String SECURITY_SCHEME_NAME = "JWT";

    @Bean
    public OpenAPI api() {

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name("Authorization")))
                .info(new Info().contact(new Contact()
                        .name("Dipendente Pubico")
                        .email("dipendentepubico@protonmail.com")
                        .url("https://github.com/dipendentepubico")
                        ).description("Backed dell'applicazione Concorsi Parenti Dipendenti")
                        .license(new License().name("AGPL v3").url("https://www.gnu.org/licenses/agpl-3.0.md"))
                        .version("1.0")
                        .title("Backend CPD Swagger")
                );
    }

}
