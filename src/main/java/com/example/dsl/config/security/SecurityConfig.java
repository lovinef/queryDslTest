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
        // Spring Security에서 모든 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManager를 생성하기 위해서는 AuthenticationManagerBuilder를 사용
        // 로그인 처리 즉, 인증을 위해서는 UserDetailService를 통해서 필요한 정보들을 가져오는데, 예제에서는 서비스 클래스(memberService)에서 이를 처리
        auth.userDetailsService((memberService)).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // HttpSecurity를 통해 HTTP 요청에 대한 웹 기반 보안을 구성 가능
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()    //HttpServletRequest에 따라 접근(access)을 제한
                // 미인증 접근 허용
                .antMatchers("/login").anonymous()
                // 인증 접근 허용
                .antMatchers("/main/**").authenticated()
                .antMatchers("/user/**").hasAnyRole("USER")     // USER 권한 접근
                .antMatchers("/admin/**").hasAnyRole("ADMIN")   // ADMIN 권한 접근
                .antMatchers("/rest/**").authenticated()
                    .and()
                // 로그인 설정
                .formLogin()    // form 기반으로 인증. 로그인 정보는 기본적으로 HttpSession을 이용
                .loginPage("/login").permitAll()    // 로그인 페이지는 모든 사용자 접근 가능
                .loginProcessingUrl("/loginProcess")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                .successHandler(loginSuccessHandler)    // 로그인 성공시 핸들러
                .failureHandler(loginFailureHandler)    // 로그인 실패시 핸들러
//                .failureForwardUrl("/login")          // 로그인 실패시 forward url, 핸들러 사용시 설정하지 않음
                    .and()
                .logout()
                    .logoutUrl("/logout")           // logout url 설정, 화면에서 해당 url 호출시 자동 로그아웃된다(컨트롤러 구현필요 없음)
                    .invalidateHttpSession(true)    // HTTP 세션 초기화 여부
        ;
    }
}

