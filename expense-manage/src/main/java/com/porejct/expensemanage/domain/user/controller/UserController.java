package com.porejct.expensemanage.domain.user.controller;

import static com.porejct.expensemanage.commone.dto.UserResponseStatus.USER_CREATE;

import com.porejct.expensemanage.commone.dto.ResponseDto;
import com.porejct.expensemanage.commone.utils.response.UrlCreator;
import com.porejct.expensemanage.domain.user.dto.request.UserPostRequest;
import com.porejct.expensemanage.domain.user.dto.response.UserIdResponse;
import com.porejct.expensemanage.domain.user.entity.User;
import com.porejct.expensemanage.domain.user.mapper.UserMapper;
import com.porejct.expensemanage.domain.user.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    private static final String DEFAULT = "/api/users";

    @PostMapping
    public ResponseEntity<ResponseDto<UserIdResponse>> postUser(@RequestBody @Valid UserPostRequest dto) {
        User result = service.postUser(mapper.toEntity(dto));
        ResponseDto<UserIdResponse> response = mapper.toDto(result, USER_CREATE);
        URI location = UrlCreator.createUri(DEFAULT, result.getId());

        return ResponseEntity.created(location).body(response);
    }

}
