package com.tz.campon.mainpage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Main 페이지 요청 처리
    @GetMapping("/main")
    public String main(Authentication authentication, Model model) {
        // 로그인 상태 확인
        boolean isLoggedIn = authentication != null;

        // 모델에 로그인 상태 전달
        model.addAttribute("isLoggedIn", isLoggedIn);

        // 메인 페이지 정보 전달
        model.addAttribute("title", "캠핑 예약 사이트");
        model.addAttribute("description", "캠핑장 예약을 쉽고 간편하게!");

        return "mainpage/main";  // mainpage/main.html로 이동
    }
}
