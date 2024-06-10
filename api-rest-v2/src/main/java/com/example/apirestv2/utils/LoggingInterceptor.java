package com.example.apirestv2.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Log request details
        String requestURL = request.getRequestURL().toString();
        String httpMethod = request.getMethod();
        String clientIP = request.getRemoteAddr();

        // Store information in request attributes for later use
        request.setAttribute("requestURL", requestURL);
        request.setAttribute("httpMethod", httpMethod);
        request.setAttribute("clientIP", clientIP);

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURL = (String) request.getAttribute("requestURL");
        String httpMethod = (String) request.getAttribute("httpMethod");
        String clientIP = (String) request.getAttribute("clientIP");
        String responseStatus = String.valueOf(response.getStatus());

        Logger.getInstance().log(requestURL, clientIP, httpMethod, responseStatus, ex == null ? "" : ex.getMessage());
    }
}