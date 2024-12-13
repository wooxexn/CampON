package com.tz.campon.intro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroController {

    @GetMapping("/")
    public String showIntroPage() {
        return "intro/Intro";  // /intro/Intro.html 파일을 반환
    }
}