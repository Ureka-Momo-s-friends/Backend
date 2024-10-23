package io.bootify.momo.repos;

import io.bootify.momo.domain.Address;
import io.bootify.momo.domain.Member;
import io.bootify.momo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByMember(Member member);

    Order findFirstByAddress(Address address);

}
