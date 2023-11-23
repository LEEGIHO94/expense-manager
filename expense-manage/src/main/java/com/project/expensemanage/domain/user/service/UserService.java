package com.project.expensemanage.domain.user.service;

import com.project.expensemanage.commone.exception.BusinessLogicException;
import com.project.expensemanage.domain.user.dto.request.UserPostRequest;
import com.project.expensemanage.domain.user.dto.response.UserIdResponse;
import com.project.expensemanage.domain.user.entity.User;
import com.project.expensemanage.domain.user.exception.UserExceptionCode;
import com.project.expensemanage.domain.user.mapper.UserMapper;
import com.project.expensemanage.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    public UserIdResponse postUser(UserPostRequest post) {
        validUserExist(post.email());
        User savedEntity = repository.save(mapper.toEntity(post, encoder.encode(post.password())));
        return mapper.toIdDto(savedEntity);
    }

    private void validUserExist(String email) {
        repository.findByEmail(email).ifPresent(d -> {
            throw new BusinessLogicException(UserExceptionCode.USER_EXIST);
        });
    }
}
