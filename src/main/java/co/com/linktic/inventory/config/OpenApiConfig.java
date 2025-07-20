package co.com.linktic.inventory.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * Configures and returns an OpenAPI instance for API documentation and security purposes.
     * It sets up a security scheme using API key-based authentication, which requires a key
     * to be provided in the "x-api-key" header of HTTP requests.
     *
     * @return an instance of OpenAPI configured with API key security.
     */
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("apiKey"))
                .components(new Components().addSecuritySchemes("apiKey",
                        new SecurityScheme()
                                .name("x-api-key")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)));
    }
}
