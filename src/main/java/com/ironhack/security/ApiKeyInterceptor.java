package com.ironhack.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${auth.api-key}")
    private String API_KEY;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String apiKey = request.getHeader("API-Key");

        if (apiKey == null || !apiKey.equals(API_KEY)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or invalid API-Key");
            return false;
        }

        return true;
    }
}