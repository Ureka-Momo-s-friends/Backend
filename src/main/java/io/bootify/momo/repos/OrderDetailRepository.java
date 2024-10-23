package io.bootify.momo.repos;

import io.bootify.momo.domain.Order;
import io.bootify.momo.domain.OrderDetail;
import io.bootify.momo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    OrderDetail findFirstByOrder(Order order);

    OrderDetail findFirstByProdcut(Product product);

}
