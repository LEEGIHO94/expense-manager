package com.project.expensemanage.commone.config;

import com.project.expensemanage.commone.redis.repository.RedisRepository;
import com.project.expensemanage.commone.security.filter.JwtAuthenticationFilter;
import com.project.expensemanage.commone.security.filter.JwtVerificationFilter;
import com.project.expensemanage.commone.security.handler.AuthenticationFailureCustomHandler;
import com.project.expensemanage.commone.security.utils.jwt.JwtProperties;
import com.project.expensemanage.commone.security.utils.jwt.JwtProvider;
import com.project.expensemanage.commone.utils.response.CookieUtils;
import com.project.expensemanage.commone.utils.translator.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtFilterDsl extends AbstractHttpConfigurer<JwtFilterDsl, HttpSecurity> {

  private final JwtProvider provider;
  private final JwtProperties properties;
  private final ObjectMapperUtils objectMapperUtils;
  private final RedisRepository repository;
  private final CookieUtils cookieUtils;
  private final AuthenticationFailureCustomHandler authenticationFailureCustomHandler;

  @Override
  public void configure(HttpSecurity builder) {
    AuthenticationManager authenticationManager =
        builder.getSharedObject(AuthenticationManager.class);

    JwtAuthenticationFilter jwtAuthenticationFilter =
        new JwtAuthenticationFilter(
            provider, cookieUtils, objectMapperUtils, repository, properties);
    jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");
    jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
    jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureCustomHandler);

    JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(provider, properties);

    builder
        .addFilter(jwtAuthenticationFilter)
        .addFilterBefore(jwtVerificationFilter, JwtAuthenticationFilter.class);
  }

  public JwtFilterDsl build(){
    return this;
  }
}
