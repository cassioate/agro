package org.agro.adapters.configs.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${spring.application.name}") String appName,
            @Value("${app.description}") String description,
            @Value("${app.version}") String version,
            @Value("${server.port:8002}") String serverPort) {
        Info info = new Info()
                .title(appName)
                .version(version)
                .description(description)
                .contact(
                        new Contact()
                                .name("Cássio Tessaro")
                                .email("cassioate@gmail.com"));

        Server server = new Server()
                .url("http://localhost:" + serverPort)
                .description("URL do servidor");

        return new OpenAPI()
                .components(new Components())
                .info(info)
                .addServersItem(server);
    }

}
