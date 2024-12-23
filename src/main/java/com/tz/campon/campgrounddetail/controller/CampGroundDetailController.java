package com.tz.campon.campgrounddetail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class  CampGroundDetailController {
    @GetMapping("/campgrounddetail")
    public String campGroundDetail() {
        return "campgrounddetail/campgrounddetail";
    }
}
