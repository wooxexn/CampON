package com.tz.campon.mypage.controller;

import com.tz.campon.mypage.dto.ReservationDto;
import com.tz.campon.mypage.dto.UserDto;
import com.tz.campon.mypage.service.ReservationService;
import com.tz.campon.mypage.service.UserService;
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
    private UserService userService; // 사용자 정보 서비스

    @Autowired
    private ReservationService reservationService; // 예약 서비스

    // 내정보수정 화면 (GET 요청)
    @GetMapping("/edit")
    public String editForm(Model model, Authentication authentication) {
        String userId = authentication.getName();  // 로그인한 사용자 ID
        UserDto userDto = userService.getUserInfo(userId); // 사용자 정보 조회
        model.addAttribute("user", userDto);  // 사용자 정보
        return "mypage/edit";  // edit.html 화면으로 이동
    }

    // 내정보수정 처리 (POST 요청)
    @PostMapping("/edit")
    public String updateUserInfo(@Valid @ModelAttribute UserDto userDto, BindingResult result,
                                 Authentication authentication) {
        if (result.hasErrors()) {
            return "mypage/edit";  // 에러가 있을 경우 수정 화면으로 돌아감
        }

        String userId = authentication.getName(); // 로그인한 사용자 ID

        // 사용자가 비밀번호를 변경한 경우
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            try {
                userService.updateUserPassword(userId, userDto.getPassword()); // 비밀번호 변경
            } catch (IllegalArgumentException e) {
                result.rejectValue("password", "error.password", e.getMessage());
                return "mypage/edit";
            }
        }

        userService.updateUserInfo(userId, userDto);  // ID와 DTO를 함께 전달하여 사용자 정보 업데이트
        return "redirect:/mypage";  // 마이페이지로 리다이렉트
    }

    // 예약조회 화면 (GET 요청)
    @GetMapping("/reservations")
    public String viewReservations(Model model, Authentication authentication) {
        String userId = authentication.getName();  // 로그인한 사용자 ID
        List<ReservationDto> reservations = reservationService.getUserReservations(userId); // 사용자 예약 정보 조회
        model.addAttribute("reservations", reservations);
        return "mypage/reservations";  // reservations.html 화면으로 이동
    }

    // 예약취소 처리 (POST 요청)
    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam("reservationId") int reservationId) {
        reservationService.cancelReservation(reservationId);  // 예약 취소 처리
        return "redirect:/mypage/reservations";  // 예약 조회 화면으로 리다이렉트
    }
}
