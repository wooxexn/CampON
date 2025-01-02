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
        // 요청 속성과 세션에서 prevPage 가져오기
        String prevPage = (String) request.getAttribute("prevPage");

        // Referer 가져오기
        String referer = request.getHeader("Referer");
        String refererPath = referer != null ? referer.replaceFirst("^(http[s]?://[^/]+)", "") : null;

        // 보호된 페이지 리스트
        String[] protectedPages = {
                "/mypage.*",
                "/reserve.*",
                "/board/.*",
                "/campinfo.*",
                "/campdetailView.*"
        };

        // Referer 경로가 보호된 페이지인지 확인
        boolean isProtectedPage = false;
        if (refererPath != null) {
            for (String protectedPage : protectedPages) {
                if (refererPath.matches(protectedPage)) {
                    isProtectedPage = true;
                    break;
                }
            }
        }

        // 디버깅 로그
        System.out.println("Referer: " + referer);
        System.out.println("Referer Path: " + refererPath);
        System.out.println("Is Protected Page: " + isProtectedPage);
        System.out.println("PrevPage (from request): " + prevPage);

        // 리다이렉트 처리
        if (isProtectedPage) {
            response.sendRedirect("/main"); // 보호된 페이지에서 로그아웃 시 /main으로 이동
        } else if (prevPage != null) {
            response.sendRedirect(prevPage); // 비보호 페이지에서 이전 페이지로 이동
        } else {
            response.sendRedirect("/main"); // 기본적으로 /main으로 이동
        }
    }

}
