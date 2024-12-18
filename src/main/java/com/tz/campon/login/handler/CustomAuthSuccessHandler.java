package com.tz.campon.login.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // 세션에서 이전 페이지 URL 가져오기
        String prevPage = (String) request.getSession().getAttribute("prevPage");

        // 이전 페이지가 있으면 해당 페이지로 리다이렉트
        if (prevPage != null) {
            response.sendRedirect(prevPage);
            request.getSession().removeAttribute("prevPage"); // 세션에서 제거
        } else {
            // 이전 페이지가 없으면 기본 페이지로 리다이렉트
            response.sendRedirect("/main");
        }
    }

}
