package com.example.dsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {
    private Long id;
    private String name;
    private int orderCnt;
    private LocalDateTime orderDate;
}
