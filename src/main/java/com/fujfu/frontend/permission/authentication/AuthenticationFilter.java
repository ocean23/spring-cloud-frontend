package com.fujfu.frontend.permission.authentication;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Beldon
 */
public class AuthenticationFilter implements Filter {
    private static final String MOBILE_KEY = "mobile";
    private static final String USER_ID_KEY = "userId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String mobile = servletRequest.getHeader(MOBILE_KEY);
        String userId = servletRequest.getHeader(USER_ID_KEY);
        try {
            if (StringUtils.hasText(userId)) {
                AuthenticationHolder.set(new Authentication(userId, mobile));
            }
            chain.doFilter(request, response);
        } finally {
            AuthenticationHolder.remove();
        }
    }
}
