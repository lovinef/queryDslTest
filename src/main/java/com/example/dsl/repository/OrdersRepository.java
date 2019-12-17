package com.example.dsl.repository;

import com.example.dsl.Entity.Member;
import com.example.dsl.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>, QuerydslPredicateExecutor<Member> {
    List<Orders> findByMember_Id(long memberId);
}
