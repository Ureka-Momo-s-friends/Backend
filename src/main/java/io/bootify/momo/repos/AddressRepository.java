package io.bootify.momo.repos;

import io.bootify.momo.domain.Address;
import io.bootify.momo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findFirstByMember(Member member);

}
