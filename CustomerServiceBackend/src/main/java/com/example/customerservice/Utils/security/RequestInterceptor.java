package com.example.customerservice.Utils.security;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class RequestInterceptor implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userName = httpRequest.getHeader("userName");
        if (userName == null || userName.trim().isEmpty() || userName.trim().equalsIgnoreCase("")){
            userName = "Guest";
        }
        UserRequestContext.setCurrentUser(userName);
        log.info("Request Made by: " +userName+", Request Method: "+httpRequest.getMethod() + ", Requested URI: " +httpRequest.getRequestURI() + ", at: " + LocalDateTime.now());
        chain.doFilter(request, response);
    }
}
