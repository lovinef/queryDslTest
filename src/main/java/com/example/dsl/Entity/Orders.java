package com.example.dsl.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@SequenceGenerator(
    name="ORDERS_SEQUENCE_INDEX_GEN",
    sequenceName = "ORDERS_SEQUENCE_INDEX",
    initialValue = 1,
    allocationSize = 1
)
public class Orders {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "ORDERS_SEQUENCE_INDEX_GEN"
    )
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    @Column(name = "order_cnt")
    private int orderCnt;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // setter
    // 양방향 관계 설정
    public Orders changeName(String name){
        this.name = name;
        return this;
    }

    public Orders changeOrderCnt(int orderCnt){
        this.orderCnt = orderCnt;
        return this;
    }

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    // 빌더
    protected Orders(Builder builder) {
        this.name = builder.name;
        this.orderCnt = builder.orderCnt;
        this.orderDate = builder.orderDate;
        this.member = builder.member;
    }

    public static class Builder{
        private String name = "";
        private int orderCnt = 0;
        private LocalDateTime orderDate = LocalDateTime.now();
        private Member member;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder cnt(int orderCnt){
            this.orderCnt = orderCnt;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate){
            this.orderDate = orderDate;
            return this;
        }

        public Builder member(Member member){
            this.member = member;
            return this;
        }

        public Orders build(){
            return new Orders(this);
        }
    }
}
