package com.tz.campon.reservation.controller;

import com.tz.campon.reservation.DTO.CampList;
import com.tz.campon.reservation.Repository.CampListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CampListController {
    
    private final CampListRepository repository;

    public CampListController(CampListRepository campListRepository) {
        this.repository = campListRepository;
    }
    
    @GetMapping("/camplist")
    public String getCampList(@RequestParam(name = "region", required = false) String region, @RequestParam(name = "sort", required = false) String sort, Model model){

        ArrayList<CampList> list;
        if (region != null && !region.isEmpty()) {
            if ("price".equals(sort)) {
                list = repository.getCampListByRegionSorted(region, "price");
            } else if ("rating".equals(sort)) {
                list = repository.getCampListByRegionSorted(region, "rating");
            } else {
                list = repository.getCampListByRegion(region);
            }
        } else {
            if ("price".equals(sort)) {
                list = repository.getCampsByPrice();
            } else if ("rating".equals(sort)) {
                list = repository.getCampsByRating();
            } else {
                list = repository.getAllCamp();
            }
        }
      
        model.addAttribute("camplist1", list);
        model.addAttribute("selectedRegion", region);
        model.addAttribute("selectedSort", sort);
        
        return "reservation/camplist";
    }

    @GetMapping("/camplist/region")
    public String getCampListByRegion(@RequestParam("region") String region, Model model) {
        List<CampList> campList = repository.getCampListByRegion(region);
        model.addAttribute("camplist1", campList);
        model.addAttribute("selectedRegion", region);
        return "reservation/camplist";
    }


    @GetMapping("/campinfo")
    public String getCampInfo(@RequestParam(name = "camp_id") int camp_id, Model model){

        CampList camplist = repository.getCampgroundById(camp_id);

        model.addAttribute("camplist2", camplist);

        return "reservation/campinfo";
    }

}
