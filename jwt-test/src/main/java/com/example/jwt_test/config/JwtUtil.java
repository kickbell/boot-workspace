package com.example.jwt_test.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;
//    private String secretKey = "test1234"; // 서명(Signature)을 만들 때 사용하는 '비밀 열쇠'입니다.
    private final long expirationMs = 1000 * 60; // 토큰의 유통기한입니다.

    public String generateToken(String username) { // 0개의 사용위치 신규 *
        System.out.println("secretKey :" + secretKey);
        Claims claims = Jwts.claims(); //토큰에 담을 정보 조각들(Claims)을 담는 바구니
        claims.put("username", username); //바구니에 "이 토큰의 주인은 누구(username)이다"라는 정보를 저장합니다.
        claims.put("name", "추가이름");
        /*
        본격적으로 토큰을 조립하기 시작하는 '빌더' 패턴의 시작입니다.
        .setClaims(claims): 위에서 만든 정보 바구니를 넣습니다.
        .setIssuedAt(...): 토큰이 발행된 현재 시간을 기록합니다.
        .setExpiration(...): '현재 시간 + 1분' 까지는 토큰이 유효하다. 만료 시간을 설정합니다.
        .signWith(SignatureAlgorithm.HS256, secretKey): HS256 알고리즘과 아까 설정한 비밀 키를 이용해
        암호화 서명을 합니다. 이 과정이 있어야 위변조를 막을 수 있습니다.
        .compact(): 모든 설정을 뭉쳐서 하나의 점(.)으로 구분된 긴 문자열로 반환합니다. JWT 형식.
         */
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
