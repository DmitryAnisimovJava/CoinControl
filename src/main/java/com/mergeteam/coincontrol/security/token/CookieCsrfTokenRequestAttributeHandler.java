package com.mergeteam.coincontrol.security.token;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.util.Assert;

import java.util.Arrays;

public class CookieCsrfTokenRequestAttributeHandler extends CsrfTokenRequestAttributeHandler {

    private static final String XSRF_TOKEN_NAME = "XSRF-TOKEN";
    private static final String LOGIN_REQUEST_URI = "/api/v1/login";
    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(csrfToken, "csrfToken cannot be null");
        String actualToken = null;
        if (request.getRequestURI().equals(LOGIN_REQUEST_URI)) {
            return csrfToken.getToken();
        }

        if (request.getCookies() != null) {
            actualToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(XSRF_TOKEN_NAME))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        if (actualToken == null) {
            actualToken = request.getHeader(csrfToken.getHeaderName());
        }
        if (actualToken == null) {
            actualToken = request.getParameter(csrfToken.getParameterName());
        }
        return actualToken;
    }
}
