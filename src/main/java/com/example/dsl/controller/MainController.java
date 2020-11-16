package com.example.dsl.controller;

import com.example.dsl.config.security.MemberAdaptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @RequestMapping("/admin/mainPage")
    public String adminMainPage(){
        return "admin/mainPage";
    }

    @RequestMapping("/user/mainPage")
    public String userMainPage(){
        return "user/mainPage";
    }

     /*
     * 로그인 정보 조회하여, 기본 정보 반환
     * */
    private MemberAdaptor getLoginMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof MemberAdaptor){
            return (MemberAdaptor) authentication.getPrincipal();
        }else{
            return null;
        }
    }
}

