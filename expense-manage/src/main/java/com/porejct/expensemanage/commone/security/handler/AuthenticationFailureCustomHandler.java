package com.porejct.expensemanage.commone.security.handler;

import com.porejct.expensemanage.commone.exception.ErrorResponse;
import com.porejct.expensemanage.commone.security.exception.AuthExceptionCode;
import com.porejct.expensemanage.commone.utils.response.ResponseUtils;
import com.porejct.expensemanage.commone.utils.translator.ObjectMapperUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureCustomHandler implements AuthenticationFailureHandler {
    private final ObjectMapperUtils objectMapperUtils;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        log.error("# Authentication failed: {}", exception.getMessage());
        log.error("authentication ", exception);
        getSendErrorResponse(response);
    }

    private void getSendErrorResponse(HttpServletResponse response) throws IOException {
        new ResponseUtils(response,getResponseData()).sendErrorResponse();
    }

    private String getResponseData() {
        return objectMapperUtils.toStringValue(createErrorResponse());
    }

    private ErrorResponse createErrorResponse() {
        return ErrorResponse.of(AuthExceptionCode.UNAUTHENTICATED.getHttpStatus(),
                AuthExceptionCode.UNAUTHENTICATED.getMessage());
    }
}
