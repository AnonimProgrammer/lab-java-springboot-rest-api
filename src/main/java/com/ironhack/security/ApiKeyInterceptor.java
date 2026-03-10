package com.ironhack.security;

import com.ironhack.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${auth.api-key}")
    private String API_KEY;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String apiKey = request.getHeader("API-Key");

        if (apiKey == null || !apiKey.equals(API_KEY)) {

            ErrorResponse error = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase(),
                    "Unauthorized: Invalid API Key",
                    LocalDateTime.now()
            );

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(objectMapper.writeValueAsString(error));

            return false;
        }

        return true;
    }
}