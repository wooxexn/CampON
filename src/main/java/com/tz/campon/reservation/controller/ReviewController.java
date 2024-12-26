package com.tz.campon.reservation.controller;

import com.tz.campon.reservation.DTO.Review;
import com.tz.campon.reservation.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @PostMapping("/regReview")
    public String regReview(Review review, Model model){


        try {
            reviewRepository.regReview(review);
            model.addAttribute("message", "리뷰가 등록 되었습니다.");
            return "redirect:/reviewAll?camp_id="+review.getCamp_id();

        }catch (Exception e){
            model.addAttribute("message", "리뷰 등록이 완료되지 않았습니다.");
            return "redirect:/reviewAll";
        }
    }

    @GetMapping("/regReview")
    public String regReview2(){


        return "reservation/regReview";
    }

    @GetMapping("/reviewAll")
    public String getAllReview(@RequestParam (name = "camp_id") int camp_id, Model model){

        List<Review> reviewList = reviewRepository.getReviewAll(camp_id);

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("camp_id", camp_id);

        return "reservation/review";

    }

}
