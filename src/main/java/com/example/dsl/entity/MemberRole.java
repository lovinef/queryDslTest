package com.example.dsl.entity;

import com.example.dsl.enums.MemberRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name="MEMBER_ROLE_SEQUENCE_INDEX_GEN",
        sequenceName = "MEMBER_ROLE_SEQUENCE_INDEX",
        initialValue = 1,
        allocationSize = 1
)
public class MemberRole{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_ROLE_SEQUENCE_INDEX_GEN"
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private MemberRoleEnum roles;
}
