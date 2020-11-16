package com.example.dsl.service;

import com.example.dsl.entity.Member;
import com.example.dsl.config.security.MemberAdaptor;
import com.example.dsl.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findMemberByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new MemberAdaptor(findMember);
    }
}
