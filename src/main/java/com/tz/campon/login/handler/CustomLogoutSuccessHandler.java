package com.tz.campon.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // 세션에서 이전 페이지 URL 가져오기
        HttpSession session = request.getSession(false);
        String prevPage = (session != null) ? (String) session.getAttribute("prevPage") : null;

        System.out.println("CustomLogoutSuccessHandler: Retrieved prevPage = " + prevPage);

        if (prevPage != null) {
            response.sendRedirect(prevPage); // 이전 페이지로 리다이렉트
        } else {
            response.sendRedirect("/main"); // 기본 경로로 이동
        }
    }

}
