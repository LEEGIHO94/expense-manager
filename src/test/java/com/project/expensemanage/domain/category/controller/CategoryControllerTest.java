package com.project.expensemanage.domain.category.controller;

import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.config.SecurityConfig;
import com.project.expensemanage.commone.security.annotation.WithMockCustomUser;
import com.project.expensemanage.commone.security.config.AuthTestConfig;
import com.project.expensemanage.domain.category.dto.request.PostStandardCategoryRequest;
import com.project.expensemanage.domain.category.mapper.CategoryMapper;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.service.CategoryService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CategoryController.class)
@Import({
  SecurityConfig.class,
  AuthTestConfig.class,
  CategoryService.class,
  CategoryMock.class,
  CategoryMapper.class
})
@AutoConfigureRestDocs
class CategoryControllerTest {

  @Autowired MockMvc mvc;
  @Autowired CategoryMock mock;
  @Autowired ObjectMapper objectMapper;
  @MockBean CategoryService service;

  @Test
  @DisplayName("카테고리 관리자 등록 : 성공")
  @WithMockCustomUser
  void post_standard_category_test() throws Exception {
    // given
    String content = objectMapper.writeValueAsString(mock.standardCategoryPostDto());

    BDDMockito.given(service.postCategory(any(PostStandardCategoryRequest.class)))
        .willReturn(mock.getIdDto());

    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.post("/api/categories/admin")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.categoryId").isNumber())
        .andDo(
            MockMvcRestDocumentation.document(
                "post-category-admin",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("name")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이름"),
                    PayloadDocumentation.fieldWithPath("categoryType")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이름")
                        .ignored()),
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
                    PayloadDocumentation.fieldWithPath("data.categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 식별자")),
                HeaderDocumentation.responseHeaders(
                    HeaderDocumentation.headerWithName(HttpHeaders.LOCATION)
                        .description("리소스 위치"))));
  }

  @Test
  @DisplayName("카테고리 조회 : 성공")
  @WithMockCustomUser
  void get_category_list_test() throws Exception {
    // given
    BDDMockito.given(service.getCategoryList()).willReturn(mock.getCategoryResponseList());
    // when
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/categories"));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].categoryId").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").isString())
        .andDo(
            MockMvcRestDocumentation.document(
                "get-categories",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
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
                        .type(JsonFieldType.ARRAY)
                        .description("전송 데이터"),
                    PayloadDocumentation.fieldWithPath("data[].categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 식별자"),
                    PayloadDocumentation.fieldWithPath("data[].name")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이름"))));
  }
}
