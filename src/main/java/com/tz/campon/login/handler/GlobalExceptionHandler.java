package com.tz.campon.login.handler;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    /*
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        model.addAttribute("userInfo",e.getKakaoUserInfo());
        return "register";
    }

     */

    @ExceptionHandler(Exception.class)
    public void handleGenericException(Exception e, HttpServletResponse response) throws IOException {
        if (!response.isCommitted()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Custom error message");
        } else {
            // 응답이 이미 커밋된 경우 추가 처리하지 않음
            System.err.println("Response already committed: " + e.getMessage());
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex, Model model) {
        model.addAttribute("error", "필수 요청 파라미터가 누락되었습니다: " + ex.getParameterName());
        return "error"; // 에러 페이지로 리다이렉트
    }
}
