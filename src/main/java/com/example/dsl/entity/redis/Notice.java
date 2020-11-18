package com.example.dsl.entity.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@ToString
@Builder
@Getter
@RedisHash("notice")
public class Notice {
    @Id
    private Long id;
    private String content;

    public Notice changeContent(String newContent){
        this.content = newContent;
        return this;
    }
}
