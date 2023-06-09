package com.barogo.common.config;

import com.barogo.common.security.CustomAccessDeniedHandler;
import com.barogo.common.security.CustomAuthenticationEntryPoint;
import com.barogo.common.security.JwtFilter;
import com.barogo.common.security.JwtTokenProvider;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] permitAllEndpoints = {
      "/api/registration",
      "/h2/console/**",
      "/v2/api-docs",
      "/v3/api-docs/**",
      "/actuator/**",
      "/users/**",
      "/h2-console/**",
      "/swagger-ui/**"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
    return http
        .headers().frameOptions().disable()
        .and().cors()
        .and().csrf().disable()
        .httpBasic().disable()
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(permitAllEndpoints).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        .and().build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*");
    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}

