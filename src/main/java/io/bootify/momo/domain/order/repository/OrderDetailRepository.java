package io.bootify.momo.domain.order.repository;

import io.bootify.momo.domain.order.model.Order;
import io.bootify.momo.domain.order.model.OrderDetail;
import io.bootify.momo.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    OrderDetail findFirstByOrder(Order order);

    OrderDetail findFirstByProdcut(Product product);

}
