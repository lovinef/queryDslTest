package com.example.dsl.repository;

import com.example.dsl.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersRepositoryCustom {
    List<Orders> findByMember_Id(long memberId);
}
