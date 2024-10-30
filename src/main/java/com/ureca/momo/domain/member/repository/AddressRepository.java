package com.ureca.momo.domain.member.repository;


import com.ureca.momo.domain.member.model.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<MemberAddress, Long> {

    List<MemberAddress> findAllByMemberId(Long memberId);

}
