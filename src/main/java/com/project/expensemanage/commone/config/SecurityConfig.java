package com.project.expensemanage.commone.config;

import com.project.expensemanage.commone.security.handler.AuthenticationEntryPointHandler;
import com.project.expensemanage.commone.security.handler.LogoutSuccessCustomHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationEntryPointHandler authenticationEntryPointHandler;
  private final LogoutSuccessCustomHandler logoutSuccessCustomHandler;
  private final JwtFilterDsl jwtFilterDsl;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.apply(jwtFilterDsl);
    http.authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers("/docs/index.html")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/reissue")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/categories")
                    .permitAll()
                    .anyRequest()
                    .permitAll())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            exception -> exception.authenticationEntryPoint(authenticationEntryPointHandler))
        .logout(
            logout ->
                logout.logoutSuccessHandler(logoutSuccessCustomHandler).logoutUrl("/api/logout"));
    return http.build();
  }
}
