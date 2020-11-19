package com.example.dsl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
    name="MEMBER_SEQUENCE_INDEX_GEN",
    sequenceName = "MEMBER_SEQUENCE_INDEX",
    initialValue = 1,
    allocationSize = 1
)
public class Member{
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "MEMBER_SEQUENCE_INDEX_GEN"
    )
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    private int age;

    @Column(name = "ins_date")
    @CreationTimestamp
    private LocalDateTime insDate;

    @Column(name = "upt_date")
    @UpdateTimestamp
    private LocalDateTime uptDate;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<MemberRole> roles;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Orders> orders;

    // setter
    public Member changeName(String name){
        this.name = name;
        return this;
    }
    public Member changeAge(int age){
        this.age = age;
        return this;
    }
}
