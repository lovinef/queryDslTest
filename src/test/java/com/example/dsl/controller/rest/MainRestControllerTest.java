package com.example.dsl.controller.rest;

import com.example.dsl.dto.MemberDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MainRestControllerTest {
    // mock test
    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        System.out.println("-------");
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken("user", "user", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    @Test
    public void restMainCall() throws Exception {
        mockMvc.perform(get("/rest/main"))
            .andExpect(status().isOk())
            .andExpect(content().string("this is rest main"))
            .andDo(print());
    }

    @Test
    public void listOrdersCall() throws  Exception{
        mockMvc.perform(get("/rest/getMembers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result[0]").exists())
                .andExpect(jsonPath("$.result[0].id").value(3))
                .andDo(print());
    }
}