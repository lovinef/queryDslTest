package com.example.dsl.repository;

import com.example.dsl.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{
    Optional<Member> findMemberByName(String name);
}
