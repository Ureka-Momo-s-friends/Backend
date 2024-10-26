package io.bootify.momo.domain.pet.repository;

import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.pet.model.StrayCat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StrayCatRepository extends JpaRepository<StrayCat, Long> {

    StrayCat findFirstByMember(Member member);

}
