package com.tz.campon.mypage.controller;

import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.mypage.dto.ReservationDTO;
import com.tz.campon.mypage.exception.ReservationNotFoundException;
import com.tz.campon.mypage.exception.UserNotFoundException;
import com.tz.campon.mypage.service.MyPageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    // 내 정보 조회 및 수정 화면
    @GetMapping("/edit")
    public String editForm(Authentication authentication, Model model) {
        String userId = authentication.getName(); // 로그인한 사용자 ID
        try {
            UserDTO user = myPageService.getUserInfo(userId);
            model.addAttribute("user", user);
        } catch (UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // 에러 페이지
        }
        return "mypage/edit"; // 내 정보 수정 화면
    }

    @PostMapping("/edit")
    public String updateUserInfo(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Authentication authentication, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "mypage/edit"; // 에러 시 다시 수정 화면
        }
        String userId = authentication.getName();
        try {
            myPageService.updateUserInfo(userId, userDTO);
        } catch (UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // 에러 페이지
        }
        return "redirect:/mypage/edit"; // 수정 후 다시 내 정보 화면
    }

    // 예약 조회
    @GetMapping("/reservations")
    public String viewReservations(Authentication authentication, Model model) {
        String userId = authentication.getName();
        try {
            List<ReservationDTO> reservations = myPageService.getUserReservations(userId);
            model.addAttribute("reservations", reservations);
        } catch (UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // 에러 페이지
        }
        return "mypage/reservations";
    }

    // 예약 취소
    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam("reservationId") int reservationId, Model model) {
        try {
            myPageService.cancelReservation(reservationId);
        } catch (ReservationNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // 에러 페이지
        }
        return "redirect:/mypage/reservations";
    }
}
