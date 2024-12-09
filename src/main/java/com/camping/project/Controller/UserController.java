package com.camping.project.Controller;

import com.camping.project.DTO.UserDTO;
import com.camping.project.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("id") String id, @RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("kakao_id") String kakao_id) {
        UserDTO userDTO = new UserDTO(id, email, password, name, phone, kakao_id);
        userService.register(userDTO);
        return "redirect:/login";
    }

}
