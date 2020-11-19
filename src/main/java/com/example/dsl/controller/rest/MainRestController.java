package com.example.dsl.controller.rest;

import com.example.dsl.dto.MemberDto;
import com.example.dsl.dto.OrdersDto;
import com.example.dsl.service.rest.MemberRestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainRestController {
    private final MemberRestService memberRestService;

    @GetMapping("/rest/main")
    public String restMain(){
        return "this is rest main";
    }

    @GetMapping(value = "/rest/getMembers")
    public Result<List<MemberDto>> getMembers(){
        List<MemberDto> result = memberRestService.getAllMembers();
        return new Result<>(HttpStatus.OK.value(), result);
    }

    @GetMapping("/rest/list/orders/{id}")
    public Result<List<OrdersDto>> listOrders(@PathVariable(value = "id", required = true) Long id,
                                      @RequestParam(value = "testdata", required = false) String testdata,
                                    Principal principal){

        log.info("id is : {}", id);

        List<OrdersDto> listOrders = memberRestService.getListOrders(id);
        return new Result<>(HttpStatus.OK.value(), listOrders);
    }

    @Data
    @AllArgsConstructor
    private static class Result<T> {
        private int status;
        private T result;
    }
}