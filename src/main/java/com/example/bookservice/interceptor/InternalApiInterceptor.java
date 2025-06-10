package com.example.bookservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InternalApiInterceptor implements HandlerInterceptor {

    private static final String INTERNAL_HEADER = "X-Internal-Request";
    private static final String EXPECTED_SOURCE = "reservation-service";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String headerValue = request.getHeader(INTERNAL_HEADER);

        if (EXPECTED_SOURCE.equals(headerValue)) {
            return true;
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: Requisição interna inválida.");
        return false;
    }
}