package io.bootify.momo.repos;

import io.bootify.momo.domain.Member;
import io.bootify.momo.domain.StrayCat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StrayCatRepository extends JpaRepository<StrayCat, Long> {

    StrayCat findFirstByMember(Member member);

}
