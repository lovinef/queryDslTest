package com.example.dsl.repository;

import com.example.dsl.dto.OrdersNewDto;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<OrdersNewDto> orderDateToCharDto();
}
