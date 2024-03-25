package com.project.expensemanage.commone.utils.translator;

import com.project.expensemanage.commone.config.JacksonConfig;
import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.commone.exception.CommonExceptionCode;
import com.project.expensemanage.domain.category.entity.Category;
import java.io.InputStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({JacksonConfig.class, ObjectMapperUtils.class})
class ObjectMapperUtilsTest {
  @Autowired ObjectMapperUtils utils;
  String json;
  String jsonWrong;
  Category entity;

  @BeforeEach
  void init() {
    json = "{\"id\":1,\"name\":null,\"categoryType\":null,\"totalBudget\":null}";
    jsonWrong = "{\"\"id\":\"이부분이틀린문구\",\"name\":null,\"categoryType\":null,\"totalBudget\":null}";
    entity = Category.builder().id(1L).build();
  }

  @Test
  @DisplayName("String -> Entity 변환 성공")
  void string_to_entity_success_test() {
    // given
    // when
    Category result = utils.toEntity(json, Category.class);
    // then
    Assertions.assertThat(result.getId()).isEqualTo(entity.getId());
  }

  @Test
  @DisplayName("String -> Entity 변환 실패, json 변환 에러")
  void string_to_entity_fail_test() {
    // given
    // when
    // then
    Assertions.assertThatThrownBy(() -> utils.toEntity(jsonWrong, Category.class))
        .isInstanceOf(BusinessLogicException.class)
        .hasMessage(CommonExceptionCode.TRANS_ENTITY_ERROR.getMessage());
  }

  @Test
  @DisplayName("Entity -> String 변환 성공")
  void entity_to_string_success_test() {
    // given
    // when
    String result = utils.toStringValue(entity);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isEqualTo(json);
  }

  @Test
  @DisplayName("Entity -> String 변환 실패, 변환할 수 없는 클래스")
  void entity_to_string_fail_test() {
    // given
    InputStream mock = Mockito.mock(InputStream.class);
    // when
    // then
    Assertions.assertThatThrownBy(() -> utils.toStringValue(mock))
        .isInstanceOf(BusinessLogicException.class)
        .hasMessage(CommonExceptionCode.TRANS_JSON_ERROR.getMessage());
  }

  @Test
  @DisplayName("Request 속 JSON -> Entity 변환 성공")
  void entity_to_string_within_request_success_test() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setContent(json.getBytes());
    request.setContentType("application/json");
    // when
    Category result = utils.toEntity(request, Category.class);
    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(entity.getId());
  }

  @Test
  @DisplayName("Request 속 JSON -> Entity 변환 실패")
  void entity_to_string_within_request_fail_test() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setContent(jsonWrong.getBytes());
    request.setContentType("application/json");
    // when
    // then
    Assertions.assertThatThrownBy(() -> utils.toEntity(request, Category.class))
        .isInstanceOf(BusinessLogicException.class)
        .hasMessage(CommonExceptionCode.TRANS_ENTITY_ERROR.getMessage());
  }
}
