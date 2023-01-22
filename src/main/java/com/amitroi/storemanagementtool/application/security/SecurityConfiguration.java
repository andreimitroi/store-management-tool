package com.amitroi.storemanagementtool.application.security;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @PostConstruct
  public void logActivation() {
    log.info("Spring Security activated.");
  }

  InMemoryUserDetailsManager userDetailsManager() {
    return new InMemoryUserDetailsManager(
        User.builder()
            .passwordEncoder(passwordEncoder()::encode)
            .username("user")
            .password("user")
            .roles("USER")
            .build(),
        User.builder()
            .passwordEncoder(passwordEncoder()::encode)
            .username("admin")
            .password("admin")
            .roles("USER", "ADMIN")
            .build()
    );
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    return httpSecurity
        .userDetailsService(userDetailsManager())
        .authorizeHttpRequests()
        .anyRequest().hasRole("ADMIN")
        .and()
        .httpBasic()
        .and().build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
