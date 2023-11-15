package com.porejct.expensemanage.commone.utils.response;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieUtils {
    private final CookieProperties cookieProperties;
    public Cookie createCookie(String key) {
        Cookie cookie = new Cookie(cookieProperties.getCookieName(), key);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(cookieProperties.getAcceptedUrl());
        cookie.setMaxAge(60 * cookieProperties.getLimitTime());
        return cookie;
    }

    public Cookie deleteCookie(HttpServletRequest request) {
        Cookie cookie = searchCookieProperties(validCookiesExist(request));
        cookie.setMaxAge(0);
        return cookie;
    }

    private Cookie[] validCookiesExist(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies()).orElse(new Cookie[]{});
    }

    private Cookie searchCookieProperties(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("Refresh"))
                .findFirst()
                .orElse(new Cookie("Refresh",""));
    }



}
