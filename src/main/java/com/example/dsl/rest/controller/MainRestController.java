package com.example.dsl.rest.controller;

import com.example.dsl.dto.MemberDto;
import com.example.dsl.dto.OrdersDto;
import com.example.dsl.repository.MemberRepository;
import com.example.dsl.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainRestController {
    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;

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

    @GetMapping("/rest/list/orders/{id}")
    public List<OrdersDto> listOrders(@PathVariable(value = "id", required = true) Long id,
                                      @RequestParam(value = "testdata", required = false) String testdata,
                                    Principal principal){
        System.out.println("id is : " + id);
        System.out.println("testdata is : " + testdata);
        return ordersRepository.findByMember_Id(id)
                .stream()
                .map(orders -> new OrdersDto(orders.getId(), orders.getName(), orders.getOrderCnt(), orders.getOrderDate()))
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private static class Result<T> {
        private int status;
        private T result;
    }
}