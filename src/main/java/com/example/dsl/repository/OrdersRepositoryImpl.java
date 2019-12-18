package com.example.dsl.repository;

import com.example.dsl.Entity.Orders;
import com.example.dsl.Entity.QMember;
import com.example.dsl.Entity.QOrders;
import com.example.dsl.dto.OrdersNewDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;

import javax.persistence.EntityManager;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.dsl.Entity.QMember.member;
import static com.example.dsl.Entity.QOrders.orders;

@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrdersNewDto> orderDateToCharDto() {


        return jpaQueryFactory
                .select(Projections.fields(OrdersNewDto.class,
                            orders.id.as("id")
                            , orders.name.as("name")
                            , Expressions.stringTemplate("function('to_char', {0}, '{1s}')", orders.orderDate, ConstantImpl.create("YYYY.MM.DD")).as("newDate")
//                            , Expressions.stringTemplate("YEAR({0})||MONTH({0})||DAY({0})", orders.orderDate).as("newDate")
//                            , Expressions.stringTemplate("'aaa'").as("newDate")
                        ))
                .from(orders)
                .fetch();
    }

    @Override
    public List<String> projectioinSelectTest() {
        return jpaQueryFactory
                .select(orders.name)
                .from(orders)
                .fetch();
    }

    @Override
    public List<Tuple> projectioinSelectTupleTest() {
        return jpaQueryFactory
                .select(orders.name, orders.orderDate, orders.orderCnt)
                .from(orders)
                .fetch();
    }

    @Override
    public List<Tuple> selectTupleJoinTest() {
        return jpaQueryFactory
                .select(member.name, member.id, member.age, orders.name, orders.orderDate, orders.orderCnt)
                .from(orders)
                    .innerJoin(member)
                        .on(orders.member.eq(member))
                .fetch();
    }
}
