package com.dongdong.zoo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v0.0.1")
                .title("DongDongZoo API")
                .description("특화 프로젝트 API 명세서입니다.");

        Server server = new Server();
        server.setUrl("https://j10a201.p.ssafy.io");

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
