package com.example.jwt_test.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "JWT") //해당하는 모든 메서드에 접근할 때는 토큰 입력이 필수이다.
public class AdminController {
    @GetMapping
    public String index(){
        return "admin get만 접속 가능";
    }

    @PostMapping
    public String post(){
        return "admin post 만 접속 가능";
    }
}
