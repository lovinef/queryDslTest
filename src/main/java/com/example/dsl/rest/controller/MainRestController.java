package com.example.dsl.rest.controller;

import com.example.dsl.dto.MemberDto;
import com.example.dsl.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainRestController {
    private final MemberRepository memberRepository;

    @GetMapping("/rest/main")
    public String restMain(){
        return "this is rest main";
    }

    @GetMapping("/rest/getMembers")
    public Result<List<MemberDto>> getMembers(){
        List<MemberDto> result = memberRepository.findAll()
                .stream()
                .map(member -> new MemberDto(member.getId(), member.getName(), member.getAge()))
                .collect(Collectors.toList());
        return new Result<>(HttpStatus.OK.value(), result);
    }

    @Data
    @AllArgsConstructor
    private static class Result<T> {
        private int status;
        private T data;
    }
}