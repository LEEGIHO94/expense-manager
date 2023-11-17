package com.porejct.expensemanage.domain.user.service;

import com.porejct.expensemanage.commone.exception.BusinessLogicException;
import com.porejct.expensemanage.domain.user.entity.User;
import com.porejct.expensemanage.domain.user.exception.UserExceptionCode;
import com.porejct.expensemanage.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User postUser(User request) {
        validUserExist(request.getEmail());
        return repository.save(request);
    }

    private void validUserExist(String email) {
        repository.findByEmail(email).ifPresent(d -> {
            throw new BusinessLogicException(UserExceptionCode.USER_EXIST);
        });
    }
}
