package com.example.dsl.service.rest;

import com.example.dsl.dto.MemberDto;
import com.example.dsl.dto.OrdersDto;
import com.example.dsl.repository.MemberRepository;
import com.example.dsl.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberRestService {
    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;

    public List<MemberDto> getAllMembers(){
        return memberRepository.findAll()
                .stream()
                .map(member -> modelMapper.map(member, MemberDto.class))
                .collect(Collectors.toList());
    }

    public List<OrdersDto> getListOrders(Long id){
        return ordersRepository.findByMember_Id(id)
                .stream()
                .map(orders -> modelMapper.map(orders, OrdersDto.class))
                .collect(Collectors.toList());
    }
}
