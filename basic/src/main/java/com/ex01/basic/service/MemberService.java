package com.ex01.basic.service;

import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public MemberService(){
        System.out.println("MemberService 생성자");
    }
    public void serviceTest(){
        //System.out.println("서비스 test 연결 : "+memberRepository);
        //memberRepository.repositoryTest();
    }
    public List<MemberDto> getList(){
        List<MemberDto> list = memberRepository.findAll();
        if( list.isEmpty() )
            throw new MemberNotFoundException("데이터 없다");
        return list;
    }
    public MemberDto getOne(int id ){
        //System.out.println("service id : "+id);
        //Optional<MemberDto> opt = memberRepository.findById( id );
        //return opt.orElseThrow();
        return memberRepository.findById(id)
                //.orElseThrow( () -> new MemberNotFoundException() );
                .orElseThrow( MemberNotFoundException::new );
    }
}








