package com.camping.project.Handler;

import com.camping.project.Exception.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    public String handleGenericException(Exception e, Model model) {
        model.addAttribute("error", "시스템 에러가 발생했습니다.");
        return "error"; // 에러 페이지로 이동
    }

}
