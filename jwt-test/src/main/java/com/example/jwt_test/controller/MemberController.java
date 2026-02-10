package com.example.jwt_test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    @GetMapping
    public ResponseEntity<String> getList(){
        return ResponseEntity.ok("모든 데이터 전송");
    }
}