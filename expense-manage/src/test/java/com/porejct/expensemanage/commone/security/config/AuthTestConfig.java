package com.porejct.expensemanage.commone.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.porejct.expensemanage.commone.config.JwtFilterDsl;
import com.porejct.expensemanage.commone.redis.repository.RedisRepository;
import com.porejct.expensemanage.commone.security.handler.AuthenticationEntryPointHandler;
import com.porejct.expensemanage.commone.security.handler.AuthenticationFailureCustomHandler;
import com.porejct.expensemanage.commone.security.handler.LogoutSuccessCustomHandler;
import com.porejct.expensemanage.commone.security.service.AuthService;
import com.porejct.expensemanage.commone.security.service.UserDetailsServiceImpl;
import com.porejct.expensemanage.commone.security.utils.jwt.JwtProperties;
import com.porejct.expensemanage.commone.security.utils.jwt.JwtProvider;
import com.porejct.expensemanage.commone.utils.response.CookieProperties;
import com.porejct.expensemanage.commone.utils.response.CookieUtils;
import com.porejct.expensemanage.commone.utils.response.ErrorResponseUtils;
import com.porejct.expensemanage.commone.utils.translator.ObjectMapperUtils;
import com.porejct.expensemanage.domain.user.mock.UserMock;
import com.porejct.expensemanage.domain.user.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class AuthTestConfig {

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(jwtProperties());
    }

    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public CookieProperties cookieProperties() {
        return new CookieProperties();
    }

    @Bean
    public CookieUtils cookieUtils() {
        return new CookieUtils(cookieProperties());
    }

    @Bean
    public ErrorResponseUtils errorResponseUtils() {
        return new ErrorResponseUtils(objectMapperUtils());
    }
    @Bean
    public AuthenticationFailureCustomHandler authenticationFailureCustomHandler() {
        return new AuthenticationFailureCustomHandler(errorResponseUtils());
    }

    @Bean
    public AuthenticationEntryPointHandler authenticationEntryPointHandler() {
        return new AuthenticationEntryPointHandler(objectMapperUtils());
    }

    @Bean
    public LogoutSuccessCustomHandler logoutSuccessCustomHandler() {
        return new LogoutSuccessCustomHandler(redisRepository(),cookieUtils());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository());
    }

    @Bean
    public AuthService authService() {
        return new AuthService(redisRepository(), jwtProvider(),jwtProperties(),userRepository(), objectMapperUtils(),cookieUtils());
    }

    @Bean
    public ObjectMapperUtils objectMapperUtils() {
        return new ObjectMapperUtils(objectMapper());
    }


    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public UserMock userMock() {
        return new UserMock(passwordEncoder());
    }

    @Bean
    public RedisRepository redisRepository() {
        return Mockito.mock(RedisRepository.class);
    }

    @Bean
    public JwtFilterDsl jwtFilterDsl() {
        return new JwtFilterDsl(jwtProvider(),jwtProperties(),objectMapperUtils(),redisRepository(),cookieUtils(),authenticationFailureCustomHandler());
    }
}