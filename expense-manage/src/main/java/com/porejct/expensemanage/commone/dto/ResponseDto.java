package com.porejct.expensemanage.commone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private final String timeStamp;
    private final int code;
    private final String message;
    private final T data;

    @Builder
    public ResponseDto(ResponseStatus status, T data) {
        this.timeStamp = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }
}