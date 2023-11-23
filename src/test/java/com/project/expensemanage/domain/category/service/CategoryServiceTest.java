package com.project.expensemanage.domain.category.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.category.entity.Category;
import com.project.expensemanage.domain.category.exception.CategoryExceptionCode;
import com.project.expensemanage.domain.category.mock.CategoryMock;
import com.project.expensemanage.domain.category.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({CategoryMock.class})
class CategoryServiceTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    CategoryMock mock;
    @InjectMocks
    CategoryService service;
    @Mock
    CategoryRepository repository;

    @Test
    @DisplayName("카테고리 등록 테스트 : 성공")
    void post_category_success_test() {
        // given

        given(repository.findByName(anyString())).willReturn(Optional.empty());
        Category saveMock = mock.standardEntityMock();
        given(repository.save(any(Category.class))).willReturn(saveMock);
        // when
        Category result = service.postCategory(saveMock);
        // then
        verify(repository, times(1)).findByName(anyString());
        verify(repository, times(1)).save(any(Category.class));
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(saveMock);
    }

    @Test
    @DisplayName("표준 카테고리 등록 테스트 : 실패[동일한 카테고리 이름 존재]")
    void post_category_failure_test() {
        // given

        given(repository.findByName(anyString())).willReturn(Optional.of(mock.standardEntityMock()));
        // when
        // then
        Assertions.assertThatThrownBy(() -> service.postCategory(mock.standardEntityPostMock()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(CategoryExceptionCode.CATEGORY_EXIST.getMessage());
    }


    @Test
    @DisplayName("카테고리 전체 조회 : 성공")
    void get_category_success_test() throws Exception{
        // given
        given(repository.findAllByType()).willReturn(mock.EntityListMock());
        // when
        List<Category> result = service.getCategoryList();
        // then
        Assertions.assertThat(result.stream().map((Category::getName)).toList()).containsExactlyElementsOf(mock.getNameList());
    }
}