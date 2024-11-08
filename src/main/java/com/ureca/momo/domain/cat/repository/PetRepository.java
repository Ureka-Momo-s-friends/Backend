package com.ureca.momo.domain.cat.repository;

import com.ureca.momo.domain.cat.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByMemberId(Long memberId);
}
