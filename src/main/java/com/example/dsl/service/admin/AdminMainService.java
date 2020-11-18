package com.example.dsl.service.admin;

import com.example.dsl.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminMainService {
    private final MemberRepository memberRepository;

    @Transactional
    public void updateAge(Long id, int age){
        memberRepository.findById(id).ifPresent(member -> member.changeAge(age));
    }
}
