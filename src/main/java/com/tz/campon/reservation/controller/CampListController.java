package com.tz.campon.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CampListController {
    
    @Autowired
    CampListRepository repository;
    
    @GetMapping("/camplist")
    public String getCampList(Model model){

       List<CampList> list = repository.getAllCamp();
      
       model.addAttribute("camplist1", list);
        
        return "/camplist";
    }


    @GetMapping("/campinfo")
    public String getCampInfo(@RequestParam(name = "camp_id") int camp_id, Model model){

        CampList camplist = repository.getCampgroundById(camp_id);

        model.addAttribute("camplist2", camplist);

        return "/campinfo";
    }

}
