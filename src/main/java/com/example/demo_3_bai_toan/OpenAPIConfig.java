package com.example.demo_3_bai_toan;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Value("${bezkoder.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Cuong");

        Info info = new Info()
                .title("Tutorial Management API")
                .version("1.0")
                .description("Doc Api Demo 3 bai toan");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}