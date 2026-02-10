package com.example.jwt_test.controller;

import com.example.jwt_test.dto.MemberDto;
import com.example.jwt_test.dto.MemberRegDto;
import com.example.jwt_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping
    public ResponseEntity<List<MemberDto>> getList(){
        return ResponseEntity.ok(memberService.getList() );
    }

    @PostMapping
    public ResponseEntity<Void> register(
            @ParameterObject @ModelAttribute MemberRegDto memberRegDto){
        memberService.register( memberRegDto );
        return ResponseEntity.ok().build();
    }
}