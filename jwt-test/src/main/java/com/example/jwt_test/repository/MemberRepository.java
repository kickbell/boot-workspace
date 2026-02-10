package com.example.jwt_test.repository;

import com.example.jwt_test.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


}
