package com.porejct.expensemanage.commone.utils.response;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ResponseUtils {

    private final HttpServletResponse response;
    private String body;

    public ResponseUtils(HttpServletResponse response, String body) {
        this.response = response;
        this.body = body;
    }


    public void sendErrorResponse() throws IOException {
        setResponseAuthErrorToHeader();
        response.getWriter().write(body);
    }

    private void setResponseAuthErrorToHeader() {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("utf-8");
    }
}
