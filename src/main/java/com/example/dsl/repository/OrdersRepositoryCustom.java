package com.example.dsl.repository;

import com.example.dsl.dto.OrdersNewDto;
import com.querydsl.core.Tuple;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<OrdersNewDto> orderDateToCharDto();
    List<String> projectioinSelectTest();
    List<Tuple> projectioinSelectTupleTest();
    List<Tuple> selectTupleJoinTest();
}
