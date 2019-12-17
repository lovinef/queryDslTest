package com.example.dsl.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
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

    private String name;
    private int age;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Orders> orders = new ArrayList<>();

    // setter
    public Member changeName(String name){
        this.name = name;
        return this;
    }
    public Member changeAge(int age){
        this.age = age;
        return this;
    }

    // 빌더
    protected Member(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static class Builder{
        private String name = "";
        private int age = 0;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder age(int age){
            this.age = age;
            return this;
        }

        public Member build(){
            return new Member(this);
        }
    }
}
