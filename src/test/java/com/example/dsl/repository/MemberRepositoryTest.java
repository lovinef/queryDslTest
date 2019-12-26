package com.example.dsl.repository;

import com.example.dsl.Entity.Member;
import com.example.dsl.Entity.MemberRole;
import com.example.dsl.Entity.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrdersRepository ordersRepository;

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
        memberRepository.findMemberByCase()
                .forEach(m -> {
                    System.out.println(m.toString());
                });
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
//    @Before
    @Rollback(false)
    public void makeData(){
        // 테스트 데이터 생성
        boolean makeData = true;
        if (makeData) {
            int num = 1;

            Member member1 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member2 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member3 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member4 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member5 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member6 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member7 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member8 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member9 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member10 = getNewMember("name"+num, "name"+num, num*10);  ++num;
            Member member11 = getNewMember("name"+num, "name"+num, num*10);  ++num;

            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);
            memberRepository.save(member4);
            memberRepository.save(member5);
            memberRepository.save(member6);
            memberRepository.save(member7);
            memberRepository.save(member8);
            memberRepository.save(member9);
            memberRepository.save(member10);
            memberRepository.save(member11);

            Orders order1 = Orders.builder().name("order1").orderCnt(1).orderDate(LocalDateTime.now()).member(member1).build();
            Orders order2 = Orders.builder().name("order2").orderCnt(2).orderDate(LocalDateTime.now()).member(member1).build();
            Orders order3 = Orders.builder().name("order3").orderCnt(3).orderDate(LocalDateTime.now()).member(member2).build();
            Orders order4 = Orders.builder().name("order4").orderCnt(4).orderDate(LocalDateTime.now()).member(member2).build();
            Orders order5 = Orders.builder().name("order5").orderCnt(5).orderDate(LocalDateTime.now()).member(member2).build();
            Orders order6 = Orders.builder().name("order6").orderCnt(6).orderDate(LocalDateTime.now()).member(member3).build();
            Orders order7 = Orders.builder().name("order7").orderCnt(7).orderDate(LocalDateTime.now()).member(member3).build();
            Orders order8 = Orders.builder().name("order8").orderCnt(8).orderDate(LocalDateTime.now()).member(member3).build();
            Orders order9 = Orders.builder().name("order9").orderCnt(9).orderDate(LocalDateTime.now()).member(member3).build();
            Orders order10 = Orders.builder().name("order10").orderCnt(10).orderDate(LocalDateTime.now()).member(member4).build();
            ordersRepository.save(order1);
            ordersRepository.save(order2);
            ordersRepository.save(order3);
            ordersRepository.save(order4);
            ordersRepository.save(order5);
            ordersRepository.save(order6);
            ordersRepository.save(order7);
            ordersRepository.save(order8);
            ordersRepository.save(order9);
            ordersRepository.save(order10);
        }
    }

    private Member getNewMember(String name, String password, int age) {
        Set<MemberRole> roles = new HashSet<>();
        roles.add(MemberRole.ADMIN);
        roles.add(MemberRole.USER);
        return Member.builder().name(name).password(passwordEncoder.encode(password)).age(age).roles(roles).build();
    }
}
