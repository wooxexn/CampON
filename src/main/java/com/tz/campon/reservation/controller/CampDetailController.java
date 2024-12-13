package com.tz.campon.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CampDetailController {

    @Autowired
    CampDetailRepository repository;

    @Autowired
    CampListRepository campListRepository;

    @Autowired
    ReservationRepository reservationRepository;


    @PostMapping("/campdetail")
    @ResponseBody
    public List<Reservation> getAvailableCampdetails(@RequestParam("check_in_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate check_in_date,
                                                     @RequestParam("check_out_date") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate check_out_date,
                                                     @RequestParam("camp_id") int camp_id, Model model, Reservation reservation) {


        List<Reservation> reservedCamp = reservationRepository.getReservationDate(check_in_date, check_out_date, camp_id);

        model.addAttribute("reservedCamp", reservedCamp);
        model.addAttribute("reservation", reservation);

        System.out.println(reservedCamp);
        System.out.println(check_in_date);
        System.out.println(check_out_date);


        model.addAttribute("check_in_date", check_in_date);
        model.addAttribute("check_out_date", check_out_date);
        model.addAttribute("camp_id", camp_id);

        return reservedCamp;

    }


    @GetMapping("/campdetail")
    public String getAvailable(){



        return "campdetail";
    }


}
