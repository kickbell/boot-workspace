package com.ex01.basic.service;

import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.dto.MemberRegDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.exception.InvalidLoginException;
import com.ex01.basic.exception.MemberDuplicateException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemRepository memRepository;
    @Autowired
    private MemberFileService memberFileService;


    public MemberService() {
        System.out.println("MemberService 생성자");
    }

    public void serviceTest() {
        //System.out.println("서비스 test 연결 : "+memberRepository);
        //memberRepository.repositoryTest();
    }

    public Map<String, Object> getList(int start) {
        int size = 3;
        Pageable pageable = PageRequest.of(start, size, Sort.by(Sort.Order.desc("id")));

        Page<MemberEntity> page = memRepository.findAll(pageable);
        if (page.isEmpty())
            throw new MemberNotFoundException("데이터 없다");
        Map<String, Object> map = new HashMap<>();
        map.put("list", page.stream()
                .map(memberEntity -> new MemberDto(memberEntity))
                .toList());
        map.put("totalPage", page.getTotalPages());
        map.put("currentPage", page.getNumber() + 1);

        return map;
    }

    public MemberDto getOne(int id) {
        return memRepository.findById(id)
                .map(MemberDto::new)
                .orElseThrow(MemberNotFoundException::new);
        /*return memberRepository.findById(id)
                .orElseThrow( MemberNotFoundException::new );
         */
    }

    public void modify(int id, MemberDto memberDto, MultipartFile multipartFile) {
        MemberEntity memberEntity = memRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("수정 사용자 없음"));
        String changeFileName = memberFileService.saveFile(multipartFile);
        //changeFileName : 랜덤값-db.png
        if (!changeFileName.equals("nan")) {
            memberFileService.deleteFile(memberDto.getFileName());
            memberDto.setFileName(changeFileName);
        }
        //BeanUtils.copyProperties( memberDto, memberEntity , "username", "배제할 변수명" );
        BeanUtils.copyProperties(memberDto, memberEntity);
        memRepository.save(memberEntity);
        /*
        boolean bool = memberRepository.existById( id );
        if( !bool ) //bool ==false
            throw new MemberNotFoundException("수정 사용자 없");
        memberRepository.save( id, memberDto );
         */
    }

    public void delMember(int id) {
        MemberEntity memberEntity = memRepository.findById(id).orElseThrow(
                () -> new MemberNotFoundException("삭제 사용자 없음")
        );
//        memberEntity.getPosts().forEach(post -> post.setMemberEntity(null));
        memberEntity.getPosts().clear();
        memRepository.deleteById(id);
    }

    public void insert(MemberRegDto memberRegDto, MultipartFile multipartFile) {
        boolean bool = memRepository.existsByUsername(memberRegDto.getUsername());
        if (bool)
            throw new MemberDuplicateException("중복 id");

        String fileName = memberFileService.saveFile(multipartFile);
        memberRegDto.setFileName(fileName);

        MemberEntity memberEntity = new MemberEntity();
        BeanUtils.copyProperties(memberRegDto, memberEntity);
        memRepository.save(memberEntity);
    }

    public void login(LoginDto loginDto) {
        //Optional<MemberDto> memberDto = memberRepository.findByUsername( loginDto.getUsername() );
        Optional<MemberEntity> optional =
                memRepository.findByUsername(loginDto.getUsername());
        optional.ifPresentOrElse(
                mem -> {
                    if (!mem.getPassword().equals(loginDto.getPassword()))
                        throw new InvalidLoginException("비밀번호 틀림");
                },
                () -> {
                    throw new InvalidLoginException("사용자 id 없음");
                }
        );
    }
}








