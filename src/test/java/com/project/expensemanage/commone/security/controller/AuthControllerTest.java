package com.project.expensemanage.commone.security.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.config.JwtFilterDsl;
import com.project.expensemanage.commone.config.SecurityConfig;
import com.project.expensemanage.commone.redis.repository.RedisRepository;
import com.project.expensemanage.commone.security.config.AuthTestConfig;
import com.project.expensemanage.commone.security.dto.LoginDto;
import com.project.expensemanage.commone.security.utils.jwt.JwtProvider;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.mock.UserMock;
import com.project.expensemanage.domain.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.cookies.CookieDocumentation;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AuthController.class)
@Import({AuthTestConfig.class, SecurityConfig.class, JwtFilterDsl.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AuthControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper objectMapper;
  @Autowired UserMock userMock;
  @Autowired JwtProvider jwtProvider;
  @MockBean UserRepository repository;
  @MockBean RedisRepository redis;

  @Test
  @DisplayName("로그인 테스트 : 성공")
  void login_success_test() throws Exception {
    // given
    LoginDto login = userMock.loginMock();
    User mockEntity = userMock.entityMock();
    String content = objectMapper.writeValueAsString(login);

    given(repository.findByEmail(anyString())).willReturn(Optional.of(mockEntity));

    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.post("/api/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    // then
    perform
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION))
        .andExpect(MockMvcResultMatchers.cookie().exists("Refresh"))
        .andDo(
            MockMvcRestDocumentation.document(
                "post-sign-in",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("username").description("이메일(아이디)"),
                    PayloadDocumentation.fieldWithPath("password").description("비밀번호")),
                HeaderDocumentation.responseHeaders(
                    HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("액세스 토큰")),
                CookieDocumentation.responseCookies(
                    CookieDocumentation.cookieWithName("Refresh").description("리프레시 토큰"))));
  }

  @Test
  @DisplayName("로그인 테스트 : 실패")
  void login_failure_test() throws Exception {
    // given
    LoginDto login = userMock.wrongLoginMock();
    User mockEntity = userMock.entityMock();
    String content = objectMapper.writeValueAsString(login);

    given(repository.findByEmail(anyString())).willReturn(Optional.of(mockEntity));

    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.post("/api/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    // then
    perform.andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @DisplayName("토큰 갱신 테스트 : 성공")
  void reissue_success_test() throws Exception {
    // given
    User mockEntity = userMock.entityMock();
    String mockString = objectMapper.writeValueAsString(userMock.userInfoMock());

    given(repository.findByEmail(anyString())).willReturn(Optional.of(mockEntity));
    given(redis.findByKey(anyString())).willReturn(mockString);
    willDoNothing().given(redis).save(anyString(), anyString(), anyInt());
    // when
    ResultActions perform =
        mvc.perform(MockMvcRequestBuilders.post("/api/auth/reissue").cookie(createCookie()));
    // then
    perform
        .andExpect(MockMvcResultMatchers.status().isNoContent())
        .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION))
        .andExpect(MockMvcResultMatchers.cookie().exists("Refresh"))
        .andDo(
            MockMvcRestDocumentation.document(
                "post-reissue",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                CookieDocumentation.requestCookies(
                    CookieDocumentation.cookieWithName("Refresh").description("리프레시 토큰")),
                HeaderDocumentation.responseHeaders(
                    HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("액세스 토큰(재발급)")),
                CookieDocumentation.responseCookies(
                    CookieDocumentation.cookieWithName("Refresh").description("리프레시 토큰(재발급)"))));
  }

  @Test
  @DisplayName("토큰 갱신 테스트 : 실패 [저장된 데이터가 없을 때]")
  void reissue_failure_data_not_exist_test() throws Exception {
    // given
    User mockEntity = userMock.entityMock();

    given(repository.findByEmail(anyString())).willReturn(Optional.of(mockEntity));
    given(redis.findByKey(anyString())).willReturn(null);
    willDoNothing().given(redis).save(anyString(), anyString(), anyInt());
    // when
    ResultActions perform =
        mvc.perform(MockMvcRequestBuilders.post("/api/auth/reissue").cookie(createCookie()));
    // then
    perform.andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @DisplayName("토큰 갱신 테스트 : 실패 [쿠키가 없을 때]")
  void reissue_failure_cookie_not_exist_test() throws Exception {
    // given
    User mockEntity = userMock.entityMock();
    String mockString = objectMapper.writeValueAsString(userMock.userInfoMock());

    given(repository.findByEmail(anyString())).willReturn(Optional.of(mockEntity));
    given(redis.findByKey(anyString())).willReturn(mockString);
    willDoNothing().given(redis).save(anyString(), anyString(), anyInt());
    // when
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/auth/reissue"));
    // then
    perform.andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  private Cookie createCookie() {
    return new Cookie("Refresh", jwtProvider.generateRefreshToken(userMock.getEmail()));
  }
}
