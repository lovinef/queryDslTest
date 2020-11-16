package com.example.dsl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRoleEnum {
    USER(1, "일반 사용자"),
    ADMIN(2, "관리자");

    private int code;
    private String name;
}
