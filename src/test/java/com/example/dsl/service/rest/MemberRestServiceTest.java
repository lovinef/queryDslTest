package com.example.dsl.service.rest;

import com.example.dsl.dto.MemberDto;
import com.example.dsl.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRestServiceTest {
    @Autowired
    MemberRestService memberRestService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RedisCacheManager cacheManager;

    @Test
    @Rollback(false)
    public void updateMember(){
       memberRestService.updateMember(3L, 200);
    }

    @Test
    public void getAllMembers(){
        for (MemberDto allMember : memberRestService.getAllMembers()) {
            System.out.println(allMember.toString());
        }
    }


    // CacheManager를 사용하여 직접 캐시를 조회한다.
    @Test
    public void controllCache(){
        Cache cache = cacheManager.getCache("ttl5minute");

        if(cache == null)   throw new NullPointerException("cache is null");

        Cache.ValueWrapper valueWrapper = cache.get("MemberRestService.getAllMembers()");
        if(valueWrapper == null)   throw new NullPointerException("cache value is null");

        List<MemberDto> memberDtoList = (List<MemberDto>) valueWrapper.get();
        System.out.println("----------");
        memberDtoList.forEach(memberDto -> System.out.println(memberDto.toString()));
        System.out.println("----------");
    }
}