package com.example.dsl.repository;

import com.example.dsl.entity.Member;
import com.example.dsl.entity.MemberRole;
import com.example.dsl.dto.OrdersDto;
import com.example.dsl.enums.MemberRoleEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    @Test
    @Rollback(false)
    public void where_sub_query_update_test(){
        memberRepository.findMemberByCase().forEach(m -> System.out.println(m.toString()));
    }

    @Test
    public void findMemberByCase_test(){
        memberRepository.findMemberByCase().forEach(m -> System.out.println(m.getAge()));
    }

    @Test
    @Rollback(false)
    public void findMemberByDynamicWhere_test(){
        // dynamic 쿼리 수행 결과를 엔테티로 받아 즉시 수정(JPA)
        memberRepository.findMemberByDynamicWhere(null, 20)
                .forEach(m -> m.changeAge(100));
    }

    @Test
    public void findOrdersCntGt0(){
        for (OrdersDto ordersDto : memberRepository.findOrdersCntGt0()) {
            System.out.println(ordersDto.toString());
        }
    }

    @Test
    @Rollback(false)
    public void testInsertSelect(){
        Member member = makeUserMember("test5", "test5", 10);
        memberRepository.save(member);  //  insert
//        memberRepository.flush();
        memberRepository.deleteById(8L);
        memberRepository.findById(8L).ifPresent(m -> m.changeAge(50));  // update
    }

    @Test
    public void getMemberInfo(){
        memberRepository.findAll()
                .forEach(member -> {
                    for (MemberRole role : member.getRoles()) {
                        System.out.println(member.getName() + " : " + role.getRoles().getName());
                    }
                });
    }

    @Test
    public void findMemberByName(){
        Member user = memberRepository.findMemberByName("user").get();
        Assert.assertEquals(user.getRoles().stream().filter(u -> u.getRoles().equals(MemberRoleEnum.USER)).count(), 1);
    }

    @Test
    @Rollback(false)
    public void makeData(){
        // 테스트 데이터 생성
        boolean makeData = true;
        if (makeData) {
            makeUserMember("user", "user", 10);
            makeAdminMember("admin", "admin", 20);
        }
    }

    private Member makeUserMember(String name, String password, int age) {
        Member member = Member.builder().name(name).password(passwordEncoder.encode(password)).age(age).build();
        memberRepository.save(member);

        MemberRole memberRole = MemberRole.builder().member(member).roles(MemberRoleEnum.USER).build();
        memberRoleRepository.save(memberRole);

        return member;

    }

    private Member makeAdminMember(String name, String password, int age) {
        Member member = Member.builder().name(name).password(passwordEncoder.encode(password)).age(age).build();
        memberRepository.save(member);

        MemberRole memberRole = MemberRole.builder().member(member).roles(MemberRoleEnum.ADMIN).build();
        memberRoleRepository.save(memberRole);

        return member;
    }
}
