package com.ex01.basic.controller.post;

import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.dto.post.PostDetailDto;
import com.ex01.basic.dto.post.PostDto;
import com.ex01.basic.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> insert(
            @ParameterObject @ModelAttribute PostDto postDto) {
        postService.insert(postDto);
        return ResponseEntity.ok("데이터 추가");
    }

    @GetMapping
    public ResponseEntity<List<PostAllDto>> getPost() {
        return ResponseEntity.ok(postService.getPost());
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDetailDto> getPostOne(
            @RequestParam("postId") Long postId,
            @RequestParam("memberId") Integer memberId){
        return ResponseEntity.ok(postService.getPostOne(postId, memberId));
    }

}
