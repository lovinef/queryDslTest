package com.example.dsl.dto;

import com.example.dsl.Entity.Member;
import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private int age;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
    }
}
