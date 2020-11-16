package com.example.dsl.config.handler;

import com.example.dsl.config.security.MemberAdaptor;
import com.example.dsl.entity.MemberRole;
import com.example.dsl.enums.MemberRoleEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof MemberAdaptor){
            MemberAdaptor principal = (MemberAdaptor) authentication.getPrincipal();
            List<MemberRole> roles = principal.getMember().getRoles();
            if(roles.stream().anyMatch(r -> r.getRoles().equals(MemberRoleEnum.ADMIN))){
                response.sendRedirect("/admin/mainPage");
            }else if(roles.stream().anyMatch(r -> r.getRoles().equals(MemberRoleEnum.USER))){
                response.sendRedirect("/user/mainPage");
            }
        }else{
            response.sendRedirect("/login");
        }
    }
}
