package com.project.expensemanage.commone.security.service;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.commone.redis.repository.RedisRepository;
import com.project.expensemanage.commone.security.dto.UserInfo;
import com.project.expensemanage.commone.security.exception.AuthExceptionCode;
import com.project.expensemanage.commone.security.utils.jwt.JwtProperties;
import com.project.expensemanage.commone.security.utils.jwt.JwtProvider;
import com.project.expensemanage.commone.utils.response.CookieUtils;
import com.project.expensemanage.commone.utils.translator.ObjectMapperUtils;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.exception.UserExceptionCode;
import com.project.expensemanage.domain.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final RedisRepository redis;
  private final JwtProvider provider;
  private final JwtProperties properties;
  private final UserRepository userRepository;
  private final ObjectMapperUtils objectMapperUtils;
  private final CookieUtils cookieUtils;

  public void reissue(HttpServletRequest request, HttpServletResponse response) {
    User findUser = validRefreshTokenSubject(findUserInfoData(request));

    String refresh = getRefreshToken(findUser);
    String access = getAccessToken(findUser, findUserInfoData(request));

    saveUserInfoToRedis(refresh, findUserInfoData(request));

    response.setHeader(HttpHeaders.AUTHORIZATION, access);
    response.addCookie(cookieUtils.createCookie(refresh));
  }

  private UserInfo findUserInfoData(HttpServletRequest request) {
    return findUserInfo(cookieUtils.searchCookieProperties(request));
  }

  private String getRefreshToken(User findUser) {
    return provider.generateRefreshToken(findUser.getEmail());
  }

  private String getAccessToken(User findUser, UserInfo info) {
    return properties.getPrefix()
        + provider.generateAccessToken(
            findUser.getEmail(), findUser.getId(), info.getAuthorities());
  }

  private void saveUserInfoToRedis(String refresh, UserInfo info) {
    redis.save(
        refresh,
        objectMapperUtils.toStringValue(info),
        provider.getRefreshTokenValidityInSeconds());
  }

  private User validRefreshTokenSubject(UserInfo userInfo) {
    return userRepository
        .findByEmail(userInfo.getUserName())
        .orElseThrow(() -> new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND));
  }

  private UserInfo findUserInfo(Cookie refreshCookie) {
    return objectMapperUtils.toEntity(findAndDeleteToRedis(refreshCookie), UserInfo.class);
  }

  private String findAndDeleteToRedis(Cookie refreshCookie) {
    String tokenToRedis = findTokenToRedis(refreshCookie);
    deleteToken(tokenToRedis);
    return tokenToRedis;
  }

  private void deleteToken(String tokenToRedis) {
    redis.delete(tokenToRedis);
  }

  private String findTokenToRedis(Cookie refreshCookie) {
    return Optional.ofNullable(redis.findByKey(refreshCookie.getValue()))
        .orElseThrow(() -> new BusinessLogicException(AuthExceptionCode.REFRESH_TOKEN_NOT_FOUND));
  }
}
