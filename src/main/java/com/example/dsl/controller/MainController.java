package com.example.dsl.controller;

import com.example.dsl.config.MemberAdaptor;
import com.example.dsl.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/main")
public class MainController {
    @Autowired
    MainService mainService;

    @GetMapping("/mainPage")
    public String mainPage(Model model){
        MemberAdaptor loginMember = getLoginMemberInfo();
        if(loginMember != null){
            System.out.println("loginMemberId : " + loginMember.getMemberId());
            model.addAttribute("loginMember", loginMember);
        }

        return "main/mainPage";
     }

     /*
     * 로그인 정보 조회하여, 기본 정보 반환
     * */
    private MemberAdaptor getLoginMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()
            && authentication.getPrincipal() instanceof MemberAdaptor
        ){
            return (MemberAdaptor) authentication.getPrincipal();
        }else{
            return null;
        }

    }
}

