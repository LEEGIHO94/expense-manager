package com.porejct.expensemanage.domain.user.mapper;

import com.porejct.expensemanage.commone.dto.ResponseDto;
import com.porejct.expensemanage.commone.dto.UserResponseStatus;
import com.porejct.expensemanage.domain.user.dto.request.UserPostRequest;
import com.porejct.expensemanage.domain.user.dto.response.UserIdResponse;
import com.porejct.expensemanage.domain.user.entity.User;
import com.porejct.expensemanage.domain.vo.Phone;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public ResponseDto<UserIdResponse> toDto(User user, UserResponseStatus status) {
        return ResponseDto.<UserIdResponse>builder()
                .data(toIdDto(user))
                .status(status)
                .build();
    }

    public UserIdResponse toIdDto(User user) {
        return UserIdResponse.builder()
                .userId(user.getId())
                .build();
    }

    public User toEntity(UserPostRequest dto) {
        return User.builder()
                .password(dto.password())
                .phone(new Phone(dto.phone()))
                .email(dto.email())
                .build();
    }
}
