package com.example.jwt_test.controller;

import com.example.jwt_test.dto.MemberRegDto;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @ParameterObject
            @ModelAttribute
            MemberRegDto memberRegDto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberRegDto.getUsername(),
                memberRegDto.getPassword()
        );

        // 1. 인증 시도 (이미지 내 token 변수는 별도 정의 필요)
        Authentication authentication = authenticationManager.authenticate(token);
        System.out.println("인증된 사용자 정보 : " + authentication.getPrincipal());

        // 2. 인증된 사용자 상세 정보 추출
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        System.out.println("name : " + authentication.getName() );
        System.out.println("name : " + userDetails.getUsername() );
        System.out.println("auth : " + userDetails.getAuthorities() );

        return ResponseEntity.ok("성공");
    }
}
