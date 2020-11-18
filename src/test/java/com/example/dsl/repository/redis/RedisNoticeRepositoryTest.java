package com.example.dsl.repository.redis;

import com.example.dsl.entity.redis.Notice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RedisNoticeRepositoryTest {
    @Autowired
    private RedisNoticeRepository redisNoticeRepository;

    @Test
    public void save(){
        Notice notice = Notice.builder().content("test content").build();
        redisNoticeRepository.save(notice);
    }

    @Test
    public void selectAll(){
        // 출력 테스트
        redisNoticeRepository.findAll().forEach(notice ->{
            System.out.println(notice.toString());
        });
    }

    @Test
    public void selectOne(){
        Notice notice = redisNoticeRepository.findById(-2426479291860056460L).get();
        assertEquals("test content", notice.getContent());
    }

    @Test
    public void update(){
        Notice notice = redisNoticeRepository.findById(-2426479291860056460L).get();
        notice.changeContent("change content");

        redisNoticeRepository.save(notice);

        Notice updatedNotice = redisNoticeRepository.findById(-2426479291860056460L).get();

        assertEquals(updatedNotice.getContent(), "change content");
    }
}