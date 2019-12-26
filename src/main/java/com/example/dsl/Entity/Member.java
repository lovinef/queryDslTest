package com.example.dsl.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
public class Member {
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

    @ElementCollection(fetch = FetchType.EAGER) // 권한이므로 즉시 조
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roles;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    final private List<Orders> orders = new ArrayList<>();

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
