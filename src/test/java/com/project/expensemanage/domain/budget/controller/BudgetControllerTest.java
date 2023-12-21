package com.project.expensemanage.domain.budget.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.config.JacksonConfig;
import com.project.expensemanage.commone.config.SecurityConfig;
import com.project.expensemanage.commone.security.annotation.WithMockCustomUser;
import com.project.expensemanage.commone.security.config.AuthTestConfig;
import com.project.expensemanage.domain.budget.mock.BudgetMock;
import com.project.expensemanage.domain.budget.service.BudgetService;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(BudgetController.class)
@Import({
  AuthTestConfig.class,
  BudgetController.class,
  BudgetMock.class,
  JacksonConfig.class,
  SecurityConfig.class
})
@AutoConfigureRestDocs
class BudgetControllerTest {

  @Autowired MockMvc mvc;

  @MockBean BudgetService service;
  @Autowired BudgetMock mock;
  @Autowired ObjectMapper objectMapper;

  @Test
  @DisplayName("예산 등록 테스트 : 성공")
  @WithMockCustomUser
  void post_budget_success_test() throws Exception {
    String content = objectMapper.writeValueAsString(mock.postDtoMock());
    // given
    BDDMockito.given(service.postBudget(anyLong(), any(PostBudgetRequest.class)))
        .willReturn(mock.idDtoMock());
    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.post("/api/budgets")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            MockMvcRestDocumentation.document(
                "post-budget",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("budgetDate")
                        .type(JsonFieldType.STRING)
                        .description("지출 일자"),
                    PayloadDocumentation.fieldWithPath("amount")
                        .type(JsonFieldType.NUMBER)
                        .description("지출 비용"),
                    PayloadDocumentation.fieldWithPath("categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 식별자")),
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
                    PayloadDocumentation.fieldWithPath("data.budgetId")
                        .type(JsonFieldType.NUMBER)
                        .description("예산 식별자")),
                HeaderDocumentation.responseHeaders(
                    HeaderDocumentation.headerWithName(HttpHeaders.LOCATION)
                        .description("리소스 위치"))));
  }

  @Test
  @WithMockCustomUser
  @DisplayName("예산 수정 테스트")
  void patch_budget_success_test() throws Exception {
    // given
    String content = objectMapper.writeValueAsString(mock.patchDtoMock());

    BDDMockito.given(service.patchBudget(anyLong(), anyLong(), any(PatchBudgetRequest.class)))
        .willReturn(mock.idDtoMock());
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.patch("/api/budgets" + "/{budgetId}", mock.getBudgetId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentation.document(
                "patch-budget",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                RequestDocumentation.pathParameters(
                    RequestDocumentation.parameterWithName("budgetId").description("예산 식별자")),
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("amount")
                        .type(JsonFieldType.NUMBER)
                        .description("지출 비용"),
                    PayloadDocumentation.fieldWithPath("categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 식별자")),
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
                    PayloadDocumentation.fieldWithPath("data.budgetId")
                        .type(JsonFieldType.NUMBER)
                        .description("예산 식별자")),
                HeaderDocumentation.responseHeaders(
                    HeaderDocumentation.headerWithName(HttpHeaders.LOCATION)
                        .description("리소스 위치"))));
  }

  @Test
  @WithMockCustomUser
  @DisplayName("예산 다건 조회 테스트")
  void get_budget_list_success_test() throws Exception {
    // given
    BDDMockito.given(service.getBudgetList(anyLong())).willReturn(mock.getDtoMock());
    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.get("/api/budgets")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentation.document(
                "get-budgets",
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
                    PayloadDocumentation.fieldWithPath("data[].budgetId")
                        .type(JsonFieldType.NUMBER)
                        .description("예산 식별자"),
                    PayloadDocumentation.fieldWithPath("data[].date")
                        .type(JsonFieldType.STRING)
                        .description("예산 날짜"),
                    PayloadDocumentation.fieldWithPath("data[].amount")
                        .type(JsonFieldType.NUMBER)
                        .description("예산 금액"),
                    PayloadDocumentation.fieldWithPath("data[].category")
                        .type(JsonFieldType.OBJECT)
                        .description("카테고리 데이터"),
                    PayloadDocumentation.fieldWithPath("data[].category.categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 식별자"),
                    PayloadDocumentation.fieldWithPath("data[].category.categoryName")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이름"))));
  }

  @Test
  @WithMockCustomUser
  @DisplayName("예산 단건 조회 테스트")
  void get_budget_success_test() throws Exception {
    // given
    BDDMockito.given(service.getBudget(anyLong(), anyLong())).willReturn(mock.getDtoMock().get(1));
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.get("/api/budgets" + "/{budgetId}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentation.document(
                "get-budget",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                RequestDocumentation.pathParameters(
                    RequestDocumentation.parameterWithName("budgetId").description("예산 식별자")),
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
                    PayloadDocumentation.fieldWithPath("data.budgetId")
                        .type(JsonFieldType.NUMBER)
                        .description("예산 식별자"),
                    PayloadDocumentation.fieldWithPath("data.date")
                        .type(JsonFieldType.STRING)
                        .description("예산 날짜"),
                    PayloadDocumentation.fieldWithPath("data.amount")
                        .type(JsonFieldType.NUMBER)
                        .description("예산 금액"),
                    PayloadDocumentation.fieldWithPath("data.category")
                        .type(JsonFieldType.OBJECT)
                        .description("카테고리 데이터"),
                    PayloadDocumentation.fieldWithPath("data.category.categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 식별자"),
                    PayloadDocumentation.fieldWithPath("data.category.categoryName")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이름"))));
  }

  @Test
  @WithMockCustomUser
  @DisplayName("추천 예산 조회 테스트")
  void get_recommendation_budget_success_test() throws Exception {
    // given
    BDDMockito.given(service.getRecommendedAmountForCategory(anyLong())).willReturn(mock.getDto());
    // when
    ResultActions perform =
        mvc.perform(
            MockMvcRequestBuilders.get("/api/budgets" + "/recommendation")
                .param("amount", "100000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentation.document(
                "recommend-budget",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                RequestDocumentation.queryParameters(
                    RequestDocumentation.parameterWithName("amount")
                        .description("사용자가 설정할 예산의 초액")),
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
                    PayloadDocumentation.fieldWithPath("data[].amount")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 별 예산"),
                    PayloadDocumentation.fieldWithPath("data[].categoryName")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이름"))));
  }

  @Test
  @WithMockCustomUser
  @DisplayName("예산 삭제 테스트")
  void delete_budget_success_test() throws Exception {
    // given
    BDDMockito.willDoNothing().given(service).deleteBudget(anyLong(), anyLong());
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.delete("/api/budgets" + "/{budgetId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNoContent())
        .andDo(
            MockMvcRestDocumentation.document(
                "delete-budget",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                RequestDocumentation.pathParameters(
                    RequestDocumentation.parameterWithName("budgetId").description("예산 식별자"))));
  }

}
