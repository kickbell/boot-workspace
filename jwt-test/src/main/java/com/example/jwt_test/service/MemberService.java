package com.example.jwt_test.service;

import com.example.jwt_test.dto.MemberDto;
import com.example.jwt_test.exception.MemberNotFoundException;
import com.example.jwt_test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberDto> getList(){
        List<MemberDto> list = memberRepository.findAll()
                .stream().map(MemberDto::new).toList();

        if(list.isEmpty())
            throw new MemberNotFoundException("저장된 데이터 없음");

        return list;
    }
}
