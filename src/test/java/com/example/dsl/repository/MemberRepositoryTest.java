package com.example.dsl.repository;

import com.example.dsl.Entity.Member;
import com.example.dsl.Entity.Orders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrdersRepository ordersRepository;

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
        boolean makeData = false;
        if (makeData) {
            Member member1 = new Member.Builder().name("name1").age(10).build();
            Member member2 = new Member.Builder().name("name2").age(10).build();
            Member member3 = new Member.Builder().name("name3").age(10).build();
            Member member4 = new Member.Builder().name("name4").age(20).build();
            Member member5 = new Member.Builder().name("name4").age(20).build();
            Member member6 = new Member.Builder().name("name4").age(20).build();
            Member member7 = new Member.Builder().name("name4").age(20).build();
            Member member8 = new Member.Builder().name("name4").age(30).build();
            Member member9 = new Member.Builder().name("name4").age(30).build();
            Member member10 = new Member.Builder().name("name4").age(30).build();
            Member member11 = new Member.Builder().name("name4").age(30).build();
            Member member12 = new Member.Builder().name("name4").age(30).build();

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
            memberRepository.save(member12);

            Orders order1 = new Orders.Builder().name("order1").cnt(1).orderDate(LocalDateTime.now()).member(member1).build();
            Orders order2 = new Orders.Builder().name("order2").cnt(2).orderDate(LocalDateTime.now()).member(member1).build();
            Orders order3 = new Orders.Builder().name("order3").cnt(3).orderDate(LocalDateTime.now()).member(member2).build();
            Orders order4 = new Orders.Builder().name("order4").cnt(4).orderDate(LocalDateTime.now()).member(member2).build();
            Orders order5 = new Orders.Builder().name("order5").cnt(5).orderDate(LocalDateTime.now()).member(member2).build();
            Orders order6 = new Orders.Builder().name("order6").cnt(6).orderDate(LocalDateTime.now()).member(member3).build();
            Orders order7 = new Orders.Builder().name("order7").cnt(7).orderDate(LocalDateTime.now()).member(member3).build();
            Orders order8 = new Orders.Builder().name("order8").cnt(8).orderDate(LocalDateTime.now()).member(member3).build();
            Orders order9 = new Orders.Builder().name("order9").cnt(9).orderDate(LocalDateTime.now()).member(member3).build();
            Orders order10 = new Orders.Builder().name("order10").cnt(10).orderDate(LocalDateTime.now()).member(member4).build();
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
}
