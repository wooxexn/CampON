package com.tz.campon.login.handler;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomLogoutFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if ("/logout".equals(request.getRequestURI())) {
            String referer = request.getHeader("Referer");
            System.out.println("Referer: " + referer);
            if (referer != null && !referer.contains("/logout")) {
                // 세션과 요청 속성에 저장
                HttpSession session = request.getSession();
                System.out.println("Session prevPage: " + session.getAttribute("prevPage"));
                session.setAttribute("prevPage", referer);
                request.setAttribute("prevPage", referer);
                System.out.println("CustomLogoutFilter: Referer saved to session and request attribute: " + referer);
            }
        }
        filterChain.doFilter(request, response);
    }

}
