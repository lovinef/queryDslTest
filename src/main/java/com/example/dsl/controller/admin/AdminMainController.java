package com.example.dsl.controller.admin;

import com.example.dsl.service.admin.AdminMainService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class AdminMainController {
    private final AdminMainService adminMainService;

    @PostMapping("/admin/updateAge")
    public @ResponseBody Result<String> updateAge(HttpServletRequest request
            , @RequestParam(name = "id") Long id
            , @RequestParam(name = "age") int age){

        adminMainService.updateAge(id, age);

        return new Result<>(HttpStatus.OK.value(), "success");
    }

    @Data
    @AllArgsConstructor
    private static class Result<T> {
        private int status;
        private T result;
    }
}
