package com.tz.campon.mypage.controller;

import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.mypage.dto.ReservationDTO;
import com.tz.campon.mypage.exception.ReservationNotFoundException;
import com.tz.campon.mypage.exception.UserNotFoundException;
import com.tz.campon.mypage.service.MyPageService;
import com.tz.campon.reservation.DTO.CampList;
import com.tz.campon.reservation.DTO.Reservation;
import com.tz.campon.reservation.Repository.CampDetailRepository;
import com.tz.campon.reservation.Repository.CampListRepository;
import com.tz.campon.reservation.Repository.ReservationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CampListRepository campListRepository;

    @Autowired
    private CampDetailRepository campDetailRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 마이페이지 메인 화면
    @GetMapping("")
    public String viewMyPage(Authentication authentication, Model model) {
        if (authentication == null || authentication.getName() == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "error";
        }

        String userId = authentication.getName();

        try {
            // 사용자 정보 가져오기
            UserDTO user = myPageService.getUserInfo(userId);
            if (user == null) {
                throw new UserNotFoundException("사용자 정보를 찾을 수 없습니다.");
            }

            model.addAttribute("user", user);
            model.addAttribute("username", user.getName()); // 사용자 이름

            List<Reservation> reservations = reservationRepository.getReservationById(userId);
            ArrayList<CampList> campLists = campListRepository.getAllCamp();
            ArrayList<ReservationDTO> reservationDTOs = new ArrayList<>();
            reservations.forEach(reservation -> {
                campLists.forEach(campList -> {
                    if (reservation.getCampId() == campList.getCampId()) {
                        ReservationDTO reservationDTO = new ReservationDTO(reservation.getReservationId(),reservation.getCampId(),campList.getName(),campList.getLocation(),campDetailRepository.selectCampDetailOne(reservation.getCampId(),reservation.getCampdetailId()).getPrice(),campList.getPhotoUrl(),userId,reservation.getCampdetailId(),reservation.getCheckInDate(),reservation.getCheckOutDate(),reservation.getNumberOfGuest(),reservation.getTotalPrice(),campDetailRepository.selectCampDetailOne(reservation.getCampId(),reservation.getCampdetailId()).getDetailName());
                        reservationDTOs.add(reservationDTO);
                    }
                });
            });
            model.addAttribute("reservations", reservationDTOs);
        } catch (UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }

        return "mypage/mypage";
    }

    // 예약 취소 처리
    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam("reservationId") int reservationId, Model model) {
        try {
            myPageService.cancelReservation(reservationId);
        } catch (ReservationNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "redirect:/mypage";
    }

    // 사용자 정보 수정 처리
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateUserInfo(
            @RequestBody Map<String, String> userDetails,
            Authentication authentication) {

        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }

        String userId = authentication.getName();
        String currentPassword = userDetails.get("password");
        String newName = userDetails.get("name");
        String newEmail = userDetails.get("email");
        String newPhone = userDetails.get("phone");
        String newPassword = userDetails.get("newPassword");

        try {
            // 비밀번호 검증
            if (currentPassword == null || currentPassword.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "기존 비밀번호를 입력해주세요."));
            }
            if (!myPageService.validatePassword(userId, currentPassword)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "기존 비밀번호가 일치하지 않습니다."));
            }

            // 사용자 정보 업데이트
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userId);
            userDTO.setName(newName);
            userDTO.setEmail(newEmail);
            userDTO.setPhone(newPhone);

            // 새 비밀번호가 입력되었을 경우만 업데이트
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                userDTO.setPassword(passwordEncoder.encode(newPassword));
            }

            myPageService.updateUserInfo(userDTO);

            // 응답 데이터 생성
            Map<String, String> response = new HashMap<>();
            response.put("name", newName != null ? newName : "");
            response.put("email", newEmail != null ? newEmail : "");
            response.put("phone", newPhone != null ? newPhone : "");

            return ResponseEntity.ok(response);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "사용자 정보를 찾을 수 없습니다."));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "정보 수정 중 문제가 발생했습니다."));
        }
    }

    // 비밀번호 검증을 위한 API 추가
    @PostMapping("/validate-password")
    @ResponseBody
    public ResponseEntity<Map<String, String>> validatePassword(@RequestBody Map<String, String> userDetails, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }

        String userId = authentication.getName();
        String password = userDetails.get("password");

        try {
            boolean isPasswordValid = myPageService.validatePassword(userId, password); // 비밀번호 검증
            if (!isPasswordValid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "기존 비밀번호가 올바르지 않습니다."));
            }
            return ResponseEntity.ok(Map.of("message", "비밀번호가 확인되었습니다."));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "사용자 정보를 찾을 수 없습니다."));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "비밀번호 검증 중 문제가 발생했습니다."));
        }
    }
}
