package com.ureca.momo.domain.member.repository;

import com.ureca.momo.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // Google ID로 멤버 조회를 위한 메소드 추가
    Optional<Member> findByGoogleId(String googleId);

}
