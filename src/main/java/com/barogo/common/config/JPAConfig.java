package com.barogo.common.config;

import com.barogo.common.database.Audit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
class JPAConfig {

//  @Bean
//  public AuditorAware<Audit> auditorProvider() {
//    return new AuditorAwareImpl();
//  }
}