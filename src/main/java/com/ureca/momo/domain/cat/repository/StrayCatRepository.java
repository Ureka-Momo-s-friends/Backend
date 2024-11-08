package com.ureca.momo.domain.cat.repository;

import com.ureca.momo.domain.cat.model.StrayCat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrayCatRepository extends JpaRepository<StrayCat, Long> {

    List<StrayCat> findAllByMemberId(Long memberId);
}
