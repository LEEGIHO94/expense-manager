package com.porejct.expensemanage.domain.user.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.porejct.expensemanage.commone.config.PasswordEncoderConfig;
import com.porejct.expensemanage.commone.exception.BusinessLogicException;
import com.porejct.expensemanage.domain.user.dto.response.UserIdResponse;
import com.porejct.expensemanage.domain.user.entity.User;
import com.porejct.expensemanage.domain.user.exception.UserExceptionCode;
import com.porejct.expensemanage.domain.user.mapper.UserMapper;
import com.porejct.expensemanage.domain.user.mock.UserMock;
import com.porejct.expensemanage.domain.user.repository.UserRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({PasswordEncoderConfig.class, UserMock.class, UserMapper.class,UserService.class})
class UserServiceTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    UserService service;
    @MockBean
    UserRepository repository;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    UserMock mock;

    @Test
    @DisplayName("사용자 등록 테스트 : 성공")
    void post_user_success_test() {
        // given
        User saveMock = mock.entityMock();

        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(any(User.class))).willReturn(saveMock);
        // when
        UserIdResponse result = service.postUser(mock.postDtoMock());
        // then
        verify(repository, times(1)).findByEmail(anyString());
        verify(repository, times(1)).save(any(User.class));
        Assertions.assertThat(result.userId()).usingRecursiveComparison()
                .isEqualTo(mock.getUserId());
    }

    @Test
    @DisplayName("사용자 등록 테스트 : 성공")
    void post_user_failure_test() {
        // given
        given(repository.findByEmail(anyString())).willReturn(Optional.of(mock.entityMock()));
        // when
        // then
        Assertions.assertThatThrownBy(() -> service.postUser(mock.postDtoMock())).isInstanceOf(
                BusinessLogicException.class).hasMessage(UserExceptionCode.USER_EXIST.getMessage());
    }

}