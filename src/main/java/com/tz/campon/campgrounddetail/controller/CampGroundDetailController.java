package com.tz.campon.campgrounddetail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CampGroundDetailController {
    @GetMapping("/campgrounddetail")
    public String campDetail() {
        return "campgrounddetail";
    }
}
