package com.tz.campon.login.handler;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("/logout".equals(httpRequest.getRequestURI())) {
            HttpSession session = httpRequest.getSession(false);
            if (session != null) {
                String referer = httpRequest.getHeader("Referer");
                if (referer != null && !referer.contains("/login") && !referer.contains("/register")) {
                    session.setAttribute("prevPage", referer);
                    System.out.println("CustomLogoutFilter: Referer saved to session: " + referer);
                } else {
                    System.out.println("CustomLogoutFilter: Referer is null or not valid.");
                }
            } else {
                System.out.println("CustomLogoutFilter: No active session found.");
            }
        }

        chain.doFilter(request, response);
    }

}
