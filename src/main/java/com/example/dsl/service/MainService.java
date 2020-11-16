package com.example.dsl.service;

import com.example.dsl.entity.Orders;
import com.example.dsl.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {
    private final OrdersRepository ordersRepository;

    public List<Orders> getLoginUserOrders(Long id){
        return ordersRepository.findByMember_Id(id);
    }
}
