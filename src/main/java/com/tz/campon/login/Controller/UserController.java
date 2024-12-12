package com.tz.campon.login.Controller;

import com.tz.campon.login.DTO.UserDTO;
import com.tz.campon.login.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    public String login(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);

        return "login";
    }

    @GetMapping("/register")
    public String registerPage(@RequestParam(value = "kakaoId",required = false) String kakaoId, Model model,@ModelAttribute("alertMessage") String alertMessage) {
        log.debug("Register page called with alertMessage: {}", alertMessage);
        model.addAttribute("kakaoId", kakaoId);
        model.addAttribute("alertMessage", alertMessage);
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("id") String id, @RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam(value = "kakao_id", required = false) String kakao_id) {
        UserDTO userDTO = new UserDTO(id, email, password, name, phone, kakao_id);
        userService.register(userDTO);
        return "redirect:/login";
    }

}
