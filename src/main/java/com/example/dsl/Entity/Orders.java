package com.example.dsl.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
