package com.tz.campon.reservation.controller;

import com.tz.campon.reservation.Repository.CampDetailRepository;
import com.tz.campon.reservation.Repository.CampListRepository;
import com.tz.campon.reservation.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CampDetailController {

    @Autowired
    CampDetailRepository campDetailRepository;

    @Autowired
    ReservationRepository reservationRepository;


    // /campdetail-view 경로에서 템플릿 호출
    @GetMapping("/campdetailView")
    public String getCampDetailView(@RequestParam(name="camp_id") int camp_id, Model model) {

        model.addAttribute("camp_id", camp_id);

        return "campdetail"; // campdetail.html을 반환
    }
/*
    @GetMapping("/campdetail")
    public Map<String, Object> getCampDetail(@RequestParam("check_in_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate check_in_date,
                                             @RequestParam("check_out_date") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate check_out_date,
                                             @RequestParam("camp_id") int camp_id, Model model){

  */


    @ResponseBody
    @GetMapping("/campdetail")
    public Map<String, Object> getCampDetail(@RequestParam(name="check_in_date")  String  check_in_date,
                                             @RequestParam(name="check_out_date") String   check_out_date,
                                             @RequestParam(name="camp_id") int camp_id, Model model){


        // LocalDate로 변환
        LocalDate start = LocalDate.parse(check_in_date);
        LocalDate end = LocalDate.parse(check_out_date);



        List<Integer> reservedId = reservationRepository.getReservationId(start, end, camp_id);

        List<CampDetail> campDetailList = campDetailRepository.getCampDetail(camp_id);


        Map<String, Object> result = new HashMap<>();
        result.put("campDetailList", campDetailList);
        result.put("reservedId", reservedId);

        return result;


    }


}
