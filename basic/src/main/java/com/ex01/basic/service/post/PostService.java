package com.ex01.basic.service.post;

import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.dto.post.PostDetailDto;
import com.ex01.basic.dto.post.PostDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.entity.post.PostCountEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.exception.post.PostNotFoundException;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.MemberRepository;
import com.ex01.basic.repository.post.PostCountRepository;
import com.ex01.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemRepository memRepository;
    private final PostCountRepository postCountRepository;

    public void insert(PostDto postDto) {
        MemberEntity memberEntity = memRepository.findById(postDto.getNumber())
                .orElseThrow(() -> new MemberNotFoundException("회원 가입 먼저 하세요."));
        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postDto, postEntity);

//        System.out.println("setMemberEntity 호출 전? : " + (postEntity.getMemberEntity() == null));
        postEntity.setMemberEntity(memberEntity);
//        System.out.println("setMemberEntity 호출 전? : " + (postEntity.getMemberEntity() == null));

        postRepository.save(postEntity);
    }

    public List<PostAllDto> getPost() {
        List<PostEntity> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new PostNotFoundException("조회된 게시글이 없습니다.\n게시글 먼저 추가해주세요.");
        }

        return posts.stream()
                .map(PostAllDto::new)
                .toList();
    }

    public PostDetailDto getPostOne(Long postId, Integer memberId) {
        PostDetailDto postDetailDto = postRepository.findById(postId)
                .map(PostDetailDto::new)
                .orElseThrow(
                        () -> new PostNotFoundException("존재하지 않는 글")
                );
        increaseView(postId, memberId);

        return postDetailDto;
    }

    private void increaseView(Long postId, Integer memberId) {
        boolean result = postCountRepository.existsByPostEntity_IdAndMemberEntity_Id(postId, memberId);

        System.out.println("post id:" + postId);
        System.out.println("member id:" + memberId);
        System.out.println("중복 테이블 여부" + result);

        if (!result) {
            PostEntity postEntity = postRepository.getReferenceById(postId);
            MemberEntity memberEntity = memRepository.getReferenceById(memberId);
            PostCountEntity postCountEntity = new PostCountEntity(memberEntity, postEntity);

            postCountRepository.save(postCountEntity);
        } else {
            System.out.println("이미 조회한 게시글입니다.");
        }
    }

}
