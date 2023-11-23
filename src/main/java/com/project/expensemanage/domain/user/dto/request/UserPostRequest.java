package com.project.expensemanage.domain.user.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record UserPostRequest(
        @Email
        String email,
        @Length(min = 10, message = "10자리 이상 되어야 합니다.")
        @Pattern(regexp = "^(?!([A-Za-z]+|[~!@#$%^&*()_+=]+|[0-9]+)$)[A-Za-z\\d~!@#$%^&*()_+=]+$",
                message = "문자 숫자 특수문자 중 2가지 종류 이상을 사용 해야 합니다.")
        String password,
        String phone) {

}
