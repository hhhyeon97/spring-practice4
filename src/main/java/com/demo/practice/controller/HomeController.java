package com.demo.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 메인 페이지
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("username", "username");
        return "index";
    }
}
