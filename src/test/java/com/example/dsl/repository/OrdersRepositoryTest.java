package com.example.dsl.repository;


import com.example.dsl.Entity.QOrders;
import com.example.dsl.Entity.QMember;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static com.example.dsl.Entity.QMember.*;
import static com.example.dsl.Entity.QOrders.orders;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrdersRepositoryTest {
    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    public void OrderDateToCharDto(){
        ordersRepository.orderDateToCharDto();
    }

    @Test
    public void projectioinSelectTest(){
        ordersRepository.projectioinSelectTest().forEach(s -> System.out.println(s));
    }

    @Test
    public void projectioinSelectTupleTest(){
        StringBuilder sb = new StringBuilder();
        ordersRepository.projectioinSelectTupleTest().forEach(t->{
            sb.append(t.get(orders.name)).append("\t");
            sb.append(t.get(orders.orderDate)).append("\t");
            sb.append(t.get(orders.orderCnt)).append("\n");
        });

        System.out.println(sb.toString());
    }

    @Test
    public void selectTupleJoinTest(){
        StringBuilder sb = new StringBuilder();
        ordersRepository.selectTupleJoinTest().forEach(t->{
            sb.append(t.get(member.name)).append("\t");
            sb.append(t.get(member.id)).append("\t");
            sb.append(t.get(member.age)).append("\t");
            sb.append(t.get(orders.name)).append("\t");
            sb.append(t.get(orders.orderDate)).append("\t");
            sb.append(t.get(orders.orderCnt)).append("\n");
        });

        System.out.println(sb.toString());
    }
}
