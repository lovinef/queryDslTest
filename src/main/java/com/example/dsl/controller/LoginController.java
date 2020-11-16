package com.example.dsl.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            return "redirect:/main/mainPage";
        }

        return "index";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(HttpServletRequest request, @RequestParam(name = "errorMsg", required = false) String errorMsg){
        if(!StringUtils.isEmpty(errorMsg)){
            request.setAttribute("errorMsg", errorMsg);
        }
        return "login";
    }
}
