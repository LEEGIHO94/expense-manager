package com.project.expensemanage.domain.expenditure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.config.JacksonConfig;
import com.project.expensemanage.commone.config.SecurityConfig;
import com.project.expensemanage.commone.security.annotation.WithMockCustomUser;
import com.project.expensemanage.commone.security.config.AuthTestConfig;
import com.project.expensemanage.domain.expenditure.controller.dto.request.GetExpenditureList;
import com.project.expensemanage.domain.expenditure.controller.dto.request.PostExpenditureRequest;
import com.project.expensemanage.domain.expenditure.mock.ExpenditureMock;
import com.project.expensemanage.domain.expenditure.service.ExpenditureService;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(ExpenditureController.class)
@Import({AuthTestConfig.class, JacksonConfig.class, SecurityConfig.class, ExpenditureMock.class})
@AutoConfigureRestDocs
class ExpenditureControllerTest {
  final String DEFAULT = "/api/expenditures";
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper objectMapper;
  @Autowired ExpenditureMock mock;
  @MockBean ExpenditureService service;

  @Test
  @WithMockCustomUser
  @DisplayName("예산 등록 테스트")
  void post_expenditure_success_test() throws Exception {
    // given
    String content = objectMapper.writeValueAsString(mock.getPostDtoMock());

    BDDMockito.given(service.postExpenditure(Mockito.any(PostExpenditureRequest.class), anyLong()))
        .willReturn(mock.getIdMock());
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.post(DEFAULT)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            MockMvcRestDocumentation.document(
                "post-expenditure",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("expendedDate")
                        .type(JsonFieldType.STRING)
                        .description("지출 일자"),
                    PayloadDocumentation.fieldWithPath("expendedAmount")
                        .type(JsonFieldType.NUMBER)
                        .description("지출 비용"),
                    PayloadDocumentation.fieldWithPath("memo")
                        .type(JsonFieldType.STRING)
                        .description("지출 메모")
                        .optional(),
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
                    PayloadDocumentation.fieldWithPath("data.expenditureId")
                        .type(JsonFieldType.NUMBER)
                        .description("지출 식별자")),
                HeaderDocumentation.responseHeaders(
                    HeaderDocumentation.headerWithName(HttpHeaders.LOCATION)
                        .description("리소스 위치"))));
  }

  @Test
  @WithMockCustomUser
  @DisplayName("예산 등록 테스트 : 실패[과거의 예산 등록]")
  void post_expenditure_fail_test() throws Exception {
    // given
    String content = objectMapper.writeValueAsString(mock.getPostDtoMockDateBadValidation());

    BDDMockito.given(service.postExpenditure(Mockito.any(PostExpenditureRequest.class), anyLong()))
        .willReturn(mock.getIdMock());
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.post(DEFAULT)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @WithMockCustomUser
  @DisplayName("예산 등록 테스트 : 실패[음수 예산 등록]")
  void post_expenditure_fail_minus_amount_test() throws Exception {
    // given
    String content = objectMapper.writeValueAsString(mock.getPostDtoMockAmountBadValidation());

    BDDMockito.given(service.postExpenditure(Mockito.any(PostExpenditureRequest.class), anyLong()))
        .willReturn(mock.getIdMock());
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.post(DEFAULT)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @WithMockCustomUser
  @DisplayName("등록된 예산 삭제 테스트")
  void delete_expenditure_success_test() throws Exception {
    // given

    BDDMockito.given(service.postExpenditure(Mockito.any(PostExpenditureRequest.class), anyLong()))
        .willReturn(mock.getIdMock());
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.delete(DEFAULT + "/{expenditureId}", mock.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNoContent())
        .andDo(
            MockMvcRestDocumentation.document(
                "delete-expenditure",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                RequestDocumentation.pathParameters(
                    RequestDocumentation.parameterWithName("expenditureId")
                        .description("예산 식별자"))));
  }

  @Test
  @DisplayName("등록된 예산 삭제 테스트 : 실패[사용자 미 로그인]")
  void delete_expenditure_fail_test() throws Exception {
    // given

    BDDMockito.given(service.postExpenditure(Mockito.any(PostExpenditureRequest.class), anyLong()))
        .willReturn(mock.getIdMock());
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.delete(DEFAULT + "/{expenditureId}", mock.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithMockCustomUser
  @DisplayName("등록된 예산 단건 조회 테스트")
  void get_expenditure_success_test() throws Exception {
    // given
    BDDMockito.given(service.getExpenditureDetails(anyLong(), anyLong()))
        .willReturn(mock.getDtoMock());
    // when
    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.get(DEFAULT + "/{expenditureId}", mock.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentation.document(
                "get-expenditures",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                RequestDocumentation.pathParameters(
                    RequestDocumentation.parameterWithName("expenditureId").description("예산 식별자")),
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
                    PayloadDocumentation.fieldWithPath("data.expenditureId")
                        .type(JsonFieldType.NUMBER)
                        .description("지출 식별자"),
                    PayloadDocumentation.fieldWithPath("data.expendedDate")
                        .type(JsonFieldType.STRING)
                        .description("지출 날짜"),
                    PayloadDocumentation.fieldWithPath("data.memo")
                        .type(JsonFieldType.STRING)
                        .description("지출 메모"),
                    PayloadDocumentation.fieldWithPath("data.amount")
                        .type(JsonFieldType.NUMBER)
                        .description("지출 비용"),
                    PayloadDocumentation.fieldWithPath("data.category")
                        .type(JsonFieldType.OBJECT)
                        .description("카테고리 데이터"),
                    PayloadDocumentation.fieldWithPath("data.category.categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 식별자"),
                    PayloadDocumentation.fieldWithPath("data.category.name")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이름"))));
  }

  @Test
  @WithMockCustomUser
  @DisplayName("등록된 예산 다 조회 테스트")
  void get_expenditure_list_success_test() throws Exception {
    // given
    BDDMockito.given(
            service.getExpenditureListByCondition(any(GetExpenditureList.class), anyLong()))
        .willReturn(mock.getExpenditureListResponse());
    // when

    ResultActions perform =
        mvc.perform(
            RestDocumentationRequestBuilders.get(DEFAULT)
                .params(addParams())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    // then
    perform
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentation.document(
                "get-expenditures",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                RequestDocumentation.queryParameters(
                    RequestDocumentation.parameterWithName("categoryId")
                        .description("카테고리 식별자")
                        .optional(),
                    RequestDocumentation.parameterWithName("startDate")
                        .description("조회 시작일")
                        .optional(),
                    RequestDocumentation.parameterWithName("endDate")
                        .description("조회 마지막일")
                        .optional(),
                    RequestDocumentation.parameterWithName("minAmount")
                        .description("조회 최소금액")
                        .optional(),
                    RequestDocumentation.parameterWithName("maxAmount")
                        .description("조회 최대금액")
                        .optional()),
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
                    PayloadDocumentation.fieldWithPath("data.expenditureList")
                        .type(JsonFieldType.ARRAY)
                        .description("전송할 데이터들"),
                    PayloadDocumentation.fieldWithPath("data.expenditureList[].budgetId")
                        .type(JsonFieldType.NUMBER)
                        .description("지출 식별자"),
                    PayloadDocumentation.fieldWithPath("data.expenditureList[].expendedDate")
                        .type(JsonFieldType.STRING)
                        .description("지출 날짜"),
                    PayloadDocumentation.fieldWithPath("data.expenditureList[].amount")
                        .type(JsonFieldType.NUMBER)
                        .description("지출 메모"),
                    PayloadDocumentation.fieldWithPath("data.expenditureList[].category")
                        .type(JsonFieldType.OBJECT)
                        .description("카테고리 데이터"),
                    PayloadDocumentation.fieldWithPath("data.expenditureList[].category.categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("카테고리 식별자"),
                    PayloadDocumentation.fieldWithPath(
                            "data.expenditureList[].category.categoryName")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이름"),
                    PayloadDocumentation.fieldWithPath("data.expenditureList[].category.budget")
                        .type(JsonFieldType.NUMBER)
                        .description("예산"))));
  }

  private MultiValueMap<String, String> addParams() {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("categoryId", "1");
    map.add("startDate", LocalDate.now().toString());
    map.add("endDate", LocalDate.now().minusDays(30).toString());
    map.add("minAmount", "10000");
    map.add("maxAmount", "100000");
    return map;
  }
}
