package com.example.dsl.repository;

import com.example.dsl.Entity.Member;
import com.example.dsl.Entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Member> findByNameByDsl(String name){
        return jpaQueryFactory.selectFrom(QMember.member).fetch();
    }
}
