package com.example.dsl.repository;

import com.example.dsl.Entity.Orders;
import com.example.dsl.Entity.QOrders;
import com.example.dsl.dto.OrdersNewDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedStoredProcedureQuery;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.dsl.Entity.QOrders.orders;

@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrdersNewDto> orderDateToCharDto() {
//        StringExpression toChar = Expressions.stringTemplate("to_char({0}, 'YYYYMMDD')", orders.orderDate);

        return jpaQueryFactory
                .select(Projections.fields(OrdersNewDto.class,
                            orders.id.as("id")
                            , orders.name.as("name")
                            , Expressions.stringTemplate("TO_CHAR({0}, 'YYYY-MM-DD')", orders.orderDate).as("newDate")
                        ))
                .from(orders)
                .fetch();
    }
}
