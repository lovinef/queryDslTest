package com.example.dsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberOrderJoinDto {
    private Long member_id;
    private int member_age;
    private String member_name;
    private Long orders_id;
    private String orders_name;
    private int order_cnt;
}
