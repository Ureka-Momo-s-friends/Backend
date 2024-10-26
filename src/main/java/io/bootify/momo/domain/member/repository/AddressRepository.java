package io.bootify.momo.domain.member.repository;

import io.bootify.momo.domain.member.model.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findFirstByMember(Member member);

}
