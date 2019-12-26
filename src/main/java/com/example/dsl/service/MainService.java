package com.example.dsl.service;

import com.example.dsl.Entity.Orders;
import com.example.dsl.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    @Autowired
    OrdersRepository ordersRepository;

    public List<Orders> getLoginUserOrders(Long id){
        return ordersRepository.findByMember_Id(id);
    }
}
