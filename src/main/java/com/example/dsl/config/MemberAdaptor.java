package com.example.dsl.config;

import com.example.dsl.Entity.Member;
import com.example.dsl.Entity.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class MemberAdaptor extends User {
    private Member member;
    private Long id;

    public MemberAdaptor(Member member){
        super(member.getName(), member.getPassword(), authorities(member.getRoles()));
        this.member = member;
        this.id = member.getId();
    }

    public Member getMember(){
        return this.member;
    }
    public Long getMemberId(){
        return this.id;
    }

    public MemberAdaptor(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<MemberRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toSet());
    }
}
