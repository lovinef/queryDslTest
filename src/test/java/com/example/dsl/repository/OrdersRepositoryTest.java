package com.example.dsl.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

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

}
