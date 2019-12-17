package com.example.dsl.repository;

import com.example.dsl.Entity.Member;
import com.example.dsl.dto.MemberOrderCntDto;
import com.example.dsl.dto.MemberOrderJoinDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.dsl.Entity.QMember.member;
import static com.example.dsl.Entity.QOrders.orders;

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
                                MemberOrderCntDto.class, member.name
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
                    .on(member.id.eq(orders.member.id))
                .fetch()
                ;
    }
}
