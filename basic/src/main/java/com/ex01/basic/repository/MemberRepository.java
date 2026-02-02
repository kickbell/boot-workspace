package com.ex01.basic.repository;

import com.ex01.basic.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private List<MemberDto> DB;

    public MemberRepository(){
        //System.out.println("MemberRepository 생성자");
        DB = new ArrayList<>();
        DB.add(new MemberDto(1,"aaa","aaa","USER") );
        DB.add(new MemberDto(2,"bbb","bbb","USER") );
        DB.add(new MemberDto(3,"ccc","ccc","USER") );
    }
    public List<MemberDto> findAll(){
        return DB;
    }

    public void repositoryTest(){
        System.out.println("repository 연결");
        //select * from members; List<MemberDto>
    }
    public Optional<MemberDto> findById(int id ){
        //System.out.println("findbyid : "+id);
        return DB.stream().filter(mem -> mem.getId() == id )
                .findFirst();
    }
}
