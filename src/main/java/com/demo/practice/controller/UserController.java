package com.demo.practice.controller;


import com.demo.practice.dto.SignupRequestDto;
import com.demo.practice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 로그인 페이지
    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    // 회원가입
    @PostMapping("/user/signup")
    public String signup(SignupRequestDto requestDto) {
        userService.signup(requestDto);

        return "redirect:/api/user/login-page";
    }
}