package com.example.jwt_test.controller;

import com.example.jwt_test.config.JwtUtil;
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(
            @ParameterObject
            @ModelAttribute
            MemberRegDto memberRegDto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberRegDto.getUsername(),
                memberRegDto.getPassword()
        );

        // 1. 인증 시도 (이미지 내 token 변수는 별도 정의 필요)
        Authentication authentication = authenticationManager.authenticate(token);
        // 2. 인증된 사용자 상세 정보 추출
        System.out.println("인증된 사용자 정보 : " + authentication.getPrincipal());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("name : " + authentication.getName());
        System.out.println("name : " + userDetails.getUsername());
        System.out.println("auth : " + userDetails.getAuthorities());

        String resultToken = jwtUtil.generateToken(userDetails.getUsername());
//        System.out.println("resultToken : " + resultToken);

//        이거 두줄이랑 아래 컬렉션 어쩌구랑 같은 코드.
//        Map<String,Object> map = new HashMap<>();
//        map.put("token",resultToken);
        return ResponseEntity.ok(Collections.singletonMap("result",resultToken));
    }
}
