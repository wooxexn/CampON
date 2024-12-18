package com.tz.campon.login.controller;

import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.login.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("")
public class UserController {

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session, Model model) {
        // 이전 페이지 URL 저장
        String referrer = request.getHeader("Referer");
        if (referrer != null && !referrer.contains("/login")) {
            session.setAttribute("prevPage", referrer);
        }

        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);

        return "/login/login";
    }

    @GetMapping("/register")
    public String registerPage(@RequestParam(value = "kakaoId",required = false) String kakaoId, Model model,@ModelAttribute("alertMessage") String alertMessage) {
        log.debug("Register page called with alertMessage: {}", alertMessage);
        model.addAttribute("kakaoId", kakaoId);
        model.addAttribute("alertMessage", alertMessage);
        return "/login/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("id") String id, @RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam(value = "kakao_id", required = false) String kakao_id) {
        UserDTO userDTO = new UserDTO(id, email, password, name, phone, kakao_id);
        userService.register(userDTO);
        return "redirect:/login";
    }

}
