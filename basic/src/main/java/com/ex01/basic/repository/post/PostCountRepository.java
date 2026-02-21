package com.ex01.basic.repository.post;

import com.ex01.basic.entity.post.PostCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCountRepository extends JpaRepository<PostCountEntity, Long> {
    boolean existsByPostEntity_IdAndMemberEntity_Id(Long postId, Integer memberId);
}



