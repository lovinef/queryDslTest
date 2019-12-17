package com.example.dsl.repository;


import com.example.dsl.Entity.Member;
import com.example.dsl.dto.MemberOrderCntDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void test1(){
        List<Member> test1 = memberRepository.findByNameByDsl("test1");
        System.out.println(test1.size());
    }

    @Test
    public void select_sub_query_test(){
        memberRepository.findAllMemberWithOrderCnt().forEach(m -> System.out.println(m.getName() + "/" + m.getOrderCnt()));
    }

    @Test
    public void where_sub_query_test(){
        memberRepository.findMemberAsHasOrderCnt().forEach(m -> System.out.println(m.getName() + "/" + m.getOrderCnt()));
    }

    @Test
    public void inner_join_query_test(){
        memberRepository.findMemberJoinOrder().forEach(m -> System.out.println(m.toString()));
    }
}
