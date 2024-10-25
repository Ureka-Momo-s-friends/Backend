package io.bootify.momo.repos;

import io.bootify.momo.domain.Member;
import io.bootify.momo.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // 특정 사용자의 모든 고양이 목록 가져오기
    List<Pet> findByMemberId(Long memberId);

    // 특정 사용자의 첫 번째 고양이 가져오기 (기존 메서드)
    Pet findFirstByMember(Member member);

}
