package com.example.dsl.repository;

import com.example.dsl.Entity.Member;
import com.example.dsl.dto.MemberDto;
import com.example.dsl.dto.MemberOrderCntDto;
import com.example.dsl.dto.MemberOrderJoinDto;
import com.example.dsl.dto.OrdersDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findByNameByDsl(String name);
    List<MemberOrderCntDto> findAllMemberWithOrderCnt();
    List<MemberOrderCntDto> findMemberAsHasOrderCnt();
    List<OrdersDto> findOrdersCntGt0();
    List<MemberOrderJoinDto> findMemberJoinOrder();
    List<MemberDto> findMemberByCase();
    long updateOrdersById(Long id, int updateCnt);
    List<Member> findMemberByDynamicWhere(String name, int age);

}
