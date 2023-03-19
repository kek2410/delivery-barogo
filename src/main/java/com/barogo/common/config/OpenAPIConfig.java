package com.barogo.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenAPIConfig {

  private static final String TOKEN_NAME = "bearer Token";

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .servers(servers())
        .addSecurityItem(new SecurityRequirement().addList(TOKEN_NAME))
        .components(components())
        .info(info());
  }

  private List<Server> servers() {
    return List.of(new Server().url("/").description("Default Server URL"));
  }

  private Info info() {
    var apiName = "배달 주문 API";
    return new Info()
        .title(apiName)
        .description(apiName + " Documentation")
        .version("v0.1");
  }

  private Components components() {
    return new Components().securitySchemes(Map.of(TOKEN_NAME, securityScheme()));
  }

  private SecurityScheme securityScheme() {
    return new SecurityScheme()
        .name(TOKEN_NAME)
        .type(Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer")
        .in(In.HEADER);
  }

  /*
    LocalDateTime 기본 포맷팅 변경
   */
  static {
    var localDateTimeSchema = new Schema<LocalDateTime>()
        .example(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    var yearMonthSchema = new Schema<YearMonth>()
        .example(YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
    SpringDocUtils.getConfig()
        .replaceWithSchema(LocalDateTime.class, localDateTimeSchema)
        .replaceWithSchema(YearMonth.class, yearMonthSchema);
  }

}