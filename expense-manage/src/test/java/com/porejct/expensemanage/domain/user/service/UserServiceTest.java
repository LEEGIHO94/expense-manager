package com.porejct.expensemanage.domain.user.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.porejct.expensemanage.commone.config.PasswordEncoderConfig;
import com.porejct.expensemanage.commone.exception.BusinessLogicException;
import com.porejct.expensemanage.domain.user.entity.User;
import com.porejct.expensemanage.domain.user.exception.UserExceptionCode;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({PasswordEncoderConfig.class, UserMock.class})
class UserServiceTest {

    @InjectMocks
    UserService service;
    @Mock
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
        User result = service.postUser(mock.postMock());
        // then
        verify(repository, times(1)).findByEmail(anyString());
        verify(repository, times(1)).save(any(User.class));
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(saveMock);
    }

    @Test
    @DisplayName("사용자 등록 테스트 : 성공")
    void post_user_failure_test() {
        // given
        given(repository.findByEmail(anyString())).willReturn(Optional.of(mock.entityMock()));
        // when
        // then
        Assertions.assertThatThrownBy(() -> service.postUser(mock.postMock())).isInstanceOf(
                BusinessLogicException.class).hasMessage(UserExceptionCode.USER_EXIST.getMessage());
    }

}