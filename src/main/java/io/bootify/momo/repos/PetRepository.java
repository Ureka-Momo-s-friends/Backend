package io.bootify.momo.repos;

import io.bootify.momo.domain.Member;
import io.bootify.momo.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PetRepository extends JpaRepository<Pet, Long> {

    Pet findFirstByMember(Member member);

}
