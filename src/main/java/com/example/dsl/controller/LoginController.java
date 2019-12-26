package com.example.dsl.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String index(){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            return "redirect:/main/mainPage";
        }

        return "index.html";
    }


    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
}
