package com.porejct.expensemanage.domain.user.controller;

import static com.porejct.expensemanage.commone.dto.ResponseStatus.CREATE;

import com.porejct.expensemanage.commone.dto.ResponseDto;
import com.porejct.expensemanage.commone.utils.response.UrlCreator;
import com.porejct.expensemanage.domain.user.dto.request.UserPostRequest;
import com.porejct.expensemanage.domain.user.dto.response.UserIdResponse;
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

    private static final String DEFAULT = "/api/users";
    private final UserService service;

    @PostMapping
    public ResponseEntity<ResponseDto<UserIdResponse>> postUser(
            @RequestBody @Valid UserPostRequest dto) {
        ResponseDto<UserIdResponse> response = ResponseDto.<UserIdResponse>builder()
                .data(service.postUser(dto)).status(CREATE).build();
        URI location = UrlCreator.createUri(DEFAULT, response.getData().userId());

        return ResponseEntity.created(location).body(response);
    }

}
