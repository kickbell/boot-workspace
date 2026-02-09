package com.example.db_test.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.db_test.dto.MemberAllDto;
import com.example.db_test.dto.MemberModifyDto;
import com.example.db_test.dto.MemberRegDto;
import com.example.db_test.entity.MemberEntity;
import com.example.db_test.exception.MemberDuplicateException;
import com.example.db_test.exception.MemberNotFoundException;
import com.example.db_test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.util.BeanUtil;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional(readOnly = true)
    public List<MemberAllDto> getList( int start ){

        int size = 3; //한 페이지당 3개의 글 표현
        Pageable pageable = PageRequest.of(start, size,
                                        Sort.by(Sort.Order.desc("number")) );
                    // entity = [ {}, {}, {} ]
        Page<MemberEntity> page = memberRepository.findAll( pageable );
        System.out.println( page.getTotalPages() );
        List<MemberAllDto> list = page.stream()
                .map( memEntity -> new MemberAllDto(memEntity) )
                .toList();
        /*
        List<MemberAllDto> list = memberRepository.findAll( pageable )
                .stream()
                //.map( MemberAllDto::new )
                .map( memEntity -> new MemberAllDto(memEntity) )
                .toList();
         */
        if( list.isEmpty() )
            throw new MemberNotFoundException("저장 데이터 없음!!!");
        return list;

        /*
        List<MemberEntity> list = memberRepository.findAll();
        if( list.isEmpty() )
            throw new RuntimeException("저장 데이터 없음!!!");

        List<MemberAllDto> listDto = new ArrayList<>();
        for(MemberEntity mem : list ){
            listDto.add( new MemberAllDto(mem) );
        }
        return listDto;
         */
    }

    public void insert( MemberRegDto memberRegDto ){
        System.out.println("사용자 추가");
        boolean bool = memberRepository.existsByUserId( memberRegDto.getUserId() );
        if( bool )
            throw new MemberDuplicateException("동일한 id가 있음. 다른 id 입력");
        MemberEntity memberEntity = new MemberEntity();
        BeanUtils.copyProperties(memberRegDto, memberEntity);
        memberRepository.save( memberEntity );
    }
    @Transactional( readOnly = true )
    public MemberAllDto getMember(String userId ){
        return memberRepository.findByUserId( userId )
                //.map( dto -> new MemberAllDto(dto) )
                .map( MemberAllDto::new )
                .orElseThrow( () -> new MemberNotFoundException("해당 사용자 없음") );
        /*
        return memberRepository.findByUserId( userId )
                .map( dto -> {
                    MemberAllDto memberAllDto = new MemberAllDto();
                    BeanUtils.copyProperties( dto, memberAllDto );
                    return memberAllDto;
                } )

                .orElseThrow( RuntimeException::new );
         */
        /*
        MemberEntity memberEntity = memberRepository.findByUserId( userId )
                                    .orElseThrow( RuntimeException::new );
        MemberAllDto memberAllDto = new MemberAllDto();
        BeanUtils.copyProperties( memberEntity, memberAllDto );
        return memberAllDto;
         */
                //.orElseThrow( () -> new RuntimeException() );
    }
    //@Transactional
    public void delete(long id ){
        if( !memberRepository.existsById(id) )
            throw new MemberNotFoundException("삭제할 사용자가 없습니다");
        memberRepository.deleteById( id );
    }
    //@Transactional
    public void modify(long id, MemberModifyDto memberModifyDto){
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow( () -> new MemberNotFoundException("수정 사용자 없음") );
        // entity = { id : 1 , userId : aaa, userNma : 홍길동, age : 20}
        // dto = {  userNma : 김개똥, age : 20}
        BeanUtils.copyProperties(memberModifyDto, memberEntity);
        memberRepository.save( memberEntity );
    }

    @Transactional(readOnly = true)
    public MemberEntity getTestMember( long number ){
        return memberRepository.findById(number)
                .orElse( null );
    }
}
