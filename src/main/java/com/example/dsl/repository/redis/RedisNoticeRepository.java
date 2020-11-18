package com.example.dsl.repository.redis;

import com.example.dsl.entity.redis.Notice;
import org.springframework.data.repository.CrudRepository;

public interface RedisNoticeRepository extends CrudRepository<Notice, Long> {
}
