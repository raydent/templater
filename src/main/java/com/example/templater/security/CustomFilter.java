package com.example.templater.security;

import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authorization = httpServletRequest.getHeader("Authorization");
        System.out.println(authorization);
        if (authorization == null || !authorization.matches("Basic [^\\s]+")) {
            PrintWriter writer = httpServletResponse.getWriter();
            writer.println("HTTP Status 401 - " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }

    @Override
    public void destroy() {

    }
}
