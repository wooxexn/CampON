package com.tz.campon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String intro() {
        return "intro";
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("title", "캠핑 예약 사이트");
        model.addAttribute("description", "캠핑장 예약을 쉽고 간편하게!");
        return "main";
    }
}