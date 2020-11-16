package com.example.dsl.repository;

import com.example.dsl.entity.Member;
import com.example.dsl.dto.MemberDto;
import com.example.dsl.dto.MemberOrderCntDto;
import com.example.dsl.dto.MemberOrderJoinDto;
import com.example.dsl.dto.OrdersDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.dsl.entity.QMember.member;
import static com.example.dsl.entity.QOrders.orders;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> findByNameByDsl(String name) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.name.eq(name)).fetch();
    }

    @Override
    public List<MemberOrderCntDto> findAllMemberWithOrderCnt() {
        return jpaQueryFactory
                .select(
                        Projections.fields(
                                MemberOrderCntDto.class, member.name
                                , ExpressionUtils.as(
                                        JPAExpressions
                                            .select(orders.count())
                                            .from(orders)
                                            .where(orders.member.eq(member))
                                        , "orderCnt")
                                )
                        )
                .from(member)
                .fetch();
    }

    @Override
    public List<MemberOrderCntDto> findMemberAsHasOrderCnt() {
        return jpaQueryFactory
                .select(
                        Projections.fields(
                                MemberOrderCntDto.class
                                , member.name
                                , ExpressionUtils.as(
                                        JPAExpressions
                                        .select(orders.count())
                                        .from(orders)
                                        .where(orders.member.eq(member))
                                        , "orderCnt"
                                )
                        )
                )
                .from(member)
                .where( JPAExpressions
                        .select(orders.count())
                        .from(orders)
                        .where(orders.member.eq(member)).gt(0L))
                .fetch()
                ;
    }

    @Override
    public List<OrdersDto> findOrdersCntGt0() {
        return jpaQueryFactory
                .select(
                        Projections.fields(
                                OrdersDto.class
                                , orders.id.as("id")
                        )
                )
                .from(orders)
                .groupBy(orders.id)
                .having(orders.count().gt(0L))
                .fetch()
                ;
    }

    @Override
        public List<MemberOrderJoinDto> findMemberJoinOrder() {
            return jpaQueryFactory
                    .select(
                            Projections.fields(
                                MemberOrderJoinDto.class,
                                member.id.as("member_id"),
                                member.age.as("member_age"),
                                member.name.as("member_name"),
                                orders.id.as("orders_id"),
                                orders.name.as("orders_name"),
                                orders.orderCnt.as("order_cnt")
                            )
                )
                .from(member)
                .innerJoin(orders)
                    .on(member.eq(orders.member))   // 연관관계 있을 경우
                    // .on(member.id.eq(orders.member.id)) // 연관관계 없을 경우
                .fetch()
                ;
    }

    @Override
    public List<MemberDto> findMemberByCase() {
        return jpaQueryFactory
                .select(Projections.fields(MemberDto.class,
                        member.id.as("id"),
                        member.name.as("name"),
                        new CaseBuilder()
                                .when(member.age.lt(10)).then(1)
                                .when(member.age.lt(20)).then(2)
                                .otherwise(0).as("age")
                ))
                .from(member)
                .fetch()
                ;
    }

    @Override
    public long updateOrdersById(Long id, int updateCnt) {
        return jpaQueryFactory
                .update(orders)
                .set(orders.orderCnt, updateCnt)
                .where(orders.id.eq(id))
                .execute();
    }

    @Override
    public List<Member> findMemberByDynamicWhere(String name, int age) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(eqName(name), gtAge(age))
                .fetch();
    }

    private BooleanExpression eqName(String name){
        if(StringUtils.isEmpty(name)){
            return null;
        }
        return member.name.eq(name);
    }

    private BooleanExpression gtAge(int age){
        if(!(age > 0)){
            return null;
        }
        return member.age.gt(age);
    }
}
