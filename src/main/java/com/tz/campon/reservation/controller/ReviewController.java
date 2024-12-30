package com.tz.campon.reservation.controller;

import com.tz.campon.reservation.DTO.Review;
import com.tz.campon.reservation.Repository.CampListRepository;
import com.tz.campon.reservation.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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

    @Autowired
    CampListRepository campListRepository;

    @PostMapping("/regReview")
    public String regReview(Review review, Model model){

            reviewRepository.regReview(review);
            Integer averageRating = reviewRepository.calculateAverageRating(review.getCampId());
            if (averageRating != null) {
                campListRepository.updateCampRating(review.getCampId(), averageRating);
            }
            model.addAttribute("message", "리뷰가 등록 되었습니다.");
            return "redirect:/reviewAll?camp_id="+review.getCampId();

    }

    @GetMapping("/regReview")
    public String regReview2(@RequestParam(name = "camp_id") int camp_id,  Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        model.addAttribute("camp_id", camp_id);
        model.addAttribute("user_id", userId);

        System.out.println(camp_id);

        return "reservation/regReview";
    }

    @GetMapping("/reviewAll")
    public String getAllReview(@RequestParam (name = "camp_id") int camp_id,
                               @RequestParam ( required = false , defaultValue = "1" , name="currentPage") Integer currentPage,
                               Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();


        int pageSize = 5; // 한 페이지에 보여줄 데이터 수
        int grpSize = 2; // 페이지 그룹 크기

        // 총 레코드 수 조회
        int totalCount = reviewRepository.countAll(camp_id);
        totalCount = Math.max(1, totalCount); // 최소 1 보장

        List<Review> reviewList = reviewRepository.getReviewPage(camp_id ,currentPage);



        PageHandler pageHandler = new PageHandler(currentPage, totalCount, pageSize, grpSize);
        System.out.println(reviewList);


        model.addAttribute("userId", userId);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("camp_id", camp_id);
        model.addAttribute("pageHandler", pageHandler);

        return "reservation/review";

    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam (name = "campId") int campId,
                               @RequestParam (name = "reviewId") int reviewId, Model model){



        try {
            reviewRepository.deleteReview(reviewId);
        } catch (Exception e) {
            System.out.println("삭제 실패");
            e.printStackTrace(); // 예외를 기록합니다.
        }


        return "redirect:/reviewAll?camp_id=" + campId; // URL
    }

}
