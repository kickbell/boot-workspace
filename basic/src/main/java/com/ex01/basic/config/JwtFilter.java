package com.ex01.basic.config;

import com.ex01.basic.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
//        System.out.println("doFilterInternal 실행 확인");
//        System.out.println("headers :" + request.getHeader(HttpHeaders.AUTHORIZATION));
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
//            System.out.println(token);
            System.out.println(jwtUtil.validateToken(token));
        }

        if ( jwtUtil.validateToken(token) ){
            String uName = jwtUtil.getUsernameFormToken(token);
            if(uName != null){
                UserDetails userDetails = authService.loadUserByUsername(uName);
                System.out.println("role : " + userDetails.getAuthorities());
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities() //권한 처리 user or admin
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        String testName = "testname";



        filterChain.doFilter(request, response); //필터체인 다음으로 연결해주어라
    }
}
