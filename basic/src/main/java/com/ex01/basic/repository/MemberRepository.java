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
    public boolean existById(int id){
        return DB.stream().filter(mem -> mem.getId() == id)
                .findFirst()
                .isPresent();
    }
    public void save(int id,MemberDto memberDto ){
        DB.set(id-1 , memberDto); //index, 변경할 값
    }
    public boolean deleteById( int id ){
        return DB.removeIf( m -> m.getId() == id );
    }
    public void save( MemberDto memberDto ){
        DB.add( memberDto );
    }
}
