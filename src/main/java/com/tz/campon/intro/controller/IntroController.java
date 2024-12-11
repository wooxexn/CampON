package com.tz.campon.intro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroController {

    // Intro 페이지 요청 처리
    @GetMapping("/intro")
    public String intro() {
        return "intro/intro";
    }
}