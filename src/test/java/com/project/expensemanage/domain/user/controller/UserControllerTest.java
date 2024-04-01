package com.project.expensemanage.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.config.SecurityConfig;
import com.project.expensemanage.commone.security.config.AuthTestConfig;
import com.project.expensemanage.domain.user.dto.request.UserPostRequest;
import com.project.expensemanage.domain.user.mock.UserMock;
import com.project.expensemanage.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@Import({AuthTestConfig.class, SecurityConfig.class})
@AutoConfigureRestDocs
class UserControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper objectMapper;
  @Autowired UserMock userMock;
  @MockBean UserService service;

  @Test
  @DisplayName("회원 가입 테스트 : 성공")
  void post_user_success_test() throws Exception {
    // given
    UserPostRequest post = userMock.postDtoMock();
    String content = objectMapper.writeValueAsString(post);

    BDDMockito.given(service.postUser(any(UserPostRequest.class))).willReturn(userMock.idDtoMock());
    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    // then
    perform
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
        .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").isString())
        .andDo(
            MockMvcRestDocumentation.document(
                "post-user",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("email")
                        .type(JsonFieldType.STRING)
                        .description("이메일"),
                    PayloadDocumentation.fieldWithPath("password")
                        .type(JsonFieldType.STRING)
                        .description("비밀 번호"),
                    PayloadDocumentation.fieldWithPath("phone")
                        .type(JsonFieldType.STRING)
                        .description("전화 번호")),
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath("timeStamp")
                        .type(JsonFieldType.STRING)
                        .description("전송 시간"),
                    PayloadDocumentation.fieldWithPath("code")
                        .type(JsonFieldType.NUMBER)
                        .description("상태 코드"),
                    PayloadDocumentation.fieldWithPath("message")
                        .type(JsonFieldType.STRING)
                        .description("상태 메시지"),
                    PayloadDocumentation.fieldWithPath("data")
                        .type(JsonFieldType.OBJECT)
                        .description("전송 데이터"),
                    PayloadDocumentation.fieldWithPath("data.userId")
                        .type(JsonFieldType.NUMBER)
                        .description("사용자 식별자")),
                HeaderDocumentation.responseHeaders(
                    HeaderDocumentation.headerWithName(HttpHeaders.LOCATION)
                        .description("리소스 위치"))));
  }

  @Test
  @DisplayName("회원 가입 테스트 : 실패 [비밀 번호 길이 에러]")
  void post_user_fail_password_length_test() throws Exception {
    // given
    UserPostRequest post = userMock.postDtoPasswordLineWrongMock();
    String content = objectMapper.writeValueAsString(post);

    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    // then
    perform.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @DisplayName("회원 가입 테스트 : 실패 [비밀 번호 길이 에러]")
  void post_user_fail_password_type_test() throws Exception {
    // given
    UserPostRequest post = userMock.postDtoPasswordNoTwoTypeMock();
    String content = objectMapper.writeValueAsString(post);

    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    // then
    perform.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
