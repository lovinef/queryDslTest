package com.example.dsl.repository;

import com.example.dsl.Entity.Member;
import com.example.dsl.dto.MemberOrderCntDto;
import com.example.dsl.dto.MemberOrderJoinDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findByNameByDsl(String name);
    List<MemberOrderCntDto> findAllMemberWithOrderCnt();
    List<MemberOrderCntDto> findMemberAsHasOrderCnt();
    List<MemberOrderJoinDto> findMemberJoinOrder();
}
