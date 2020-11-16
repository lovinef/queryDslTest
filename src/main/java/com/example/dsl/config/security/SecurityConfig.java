package com.example.dsl.config.security;

import com.example.dsl.config.handler.LoginFailureHandler;
import com.example.dsl.config.handler.LoginSuccessHandler;
import com.example.dsl.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final LoginFailureHandler loginFailureHandler;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((memberService))
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                // 미인증 접근 허용
                .antMatchers("/login").anonymous()
                // 인증 접근 허용
                .antMatchers("/main/**").authenticated()
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/rest/**").authenticated()
                    .and()
                // 로그인 설정
                .formLogin()
                .loginPage("/login").permitAll()    // permitAll 모든 사용자 접근 가능
                .loginProcessingUrl("/loginProcess")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
//                .failureForwardUrl("/login")
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
        ;
    }
}

