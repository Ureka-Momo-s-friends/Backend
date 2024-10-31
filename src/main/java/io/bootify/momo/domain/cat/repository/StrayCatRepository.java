package io.bootify.momo.domain.cat.repository;

import io.bootify.momo.domain.cat.model.StrayCat;
import io.bootify.momo.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StrayCatRepository extends JpaRepository<StrayCat, Long> {

    StrayCat findFirstByMember(Member member);
    List<StrayCat> findAllByMemberId(Long memberId);
}
