package io.bootify.momo.domain.member.repository;

import io.bootify.momo.domain.member.model.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<MemberAddress, Long> {

    List<MemberAddress> findByMemberId(Long memberId);

}
