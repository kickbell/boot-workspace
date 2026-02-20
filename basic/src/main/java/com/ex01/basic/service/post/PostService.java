package com.ex01.basic.service.post;

import com.ex01.basic.dto.post.PostDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.MemberRepository;
import com.ex01.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemRepository memRepository;
    public void insert(PostDto postDto) {
        MemberEntity memberEntity = memRepository.findById( postDto.getNumber() )
                .orElseThrow( () -> new MemberNotFoundException("회원 가입 먼저 하세요."));
        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postDto, postEntity);

//        System.out.println("setMemberEntity 호출 전? : " + (postEntity.getMemberEntity() == null));
        postEntity.setMemberEntity(memberEntity);
//        System.out.println("setMemberEntity 호출 전? : " + (postEntity.getMemberEntity() == null));

        postRepository.save(postEntity);
    }
}
