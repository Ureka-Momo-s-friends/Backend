package io.bootify.momo.domain.order.repository;

import io.bootify.momo.domain.member.model.Address;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByMember(Member member);

    Order findFirstByAddress(Address address);

}
